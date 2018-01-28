package com.comsysto.kotlinfullstack.backend.subscription

import com.comsysto.kotlinfullstack.backend.CryptoStock
import org.reactivestreams.Publisher
import org.reactivestreams.Subscriber
import reactor.core.publisher.Flux
import reactor.core.publisher.FluxSink
import java.time.ZonedDateTime
import kotlin.properties.Delegates
import kotlin.reflect.KProperty

interface SubscriptionService : Map<String, CurrencyStockSubscription> {
    fun createSubscription(currencies: List<String>): CurrencyStockSubscription
    fun activateSubscription(sub: CurrencyStockSubscription.Pending): CurrencyStockSubscription.Active
    fun changeSubscription(sub: CurrencyStockSubscription, currencies: List<String>): CurrencyStockSubscription
}

sealed class CurrencyStockSubscription {
    abstract val id: String
    abstract val currencies: List<String>

    data class Active internal constructor(override val id: String, override val currencies: List<String>, val stream: SubscribedCurrenciesStream) : CurrencyStockSubscription()

    data class Pending internal constructor(override val id: String, override val currencies: List<String>, val since: ZonedDateTime) : CurrencyStockSubscription() {
        internal fun activate(stream: Publisher<CryptoStock>) = Active(id, currencies, SubscribedCurrenciesStream(currencies, stream))
        fun changeCurrencies(newCurrencies: List<String>) = Pending(id, newCurrencies, since)
    }
}

class SubscribedCurrenciesStream(var currencies: List<String>, stream: Publisher<CryptoStock>) : Publisher<CryptoStock> {
    lateinit var sink: FluxSink<Publisher<CryptoStock>>
    val onPropChange: (KProperty<*>, Publisher<CryptoStock>, Publisher<CryptoStock>) -> Unit = { prop, old, new ->
        sink.next(new)
    }
    var observableStream: Publisher<CryptoStock> by Delegates.observable(stream, onPropChange)
    val metaFlux: Flux<Publisher<CryptoStock>> = Flux.create {
        sink = it
        it.next(stream)
    }

    val internalFlux = metaFlux.switchMap { it ->
        it
    }

    override fun subscribe(s: Subscriber<in CryptoStock>?) = internalFlux.subscribe(s)
}
