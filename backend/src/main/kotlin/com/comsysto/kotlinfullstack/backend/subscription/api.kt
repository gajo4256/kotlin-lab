package com.comsysto.kotlinfullstack.backend.subscription

import com.comsysto.kotlinfullstack.backend.CryptoStock
import com.comsysto.kotlinfullstack.backend.DynamicSourceProducer
import org.reactivestreams.Publisher
import java.time.ZonedDateTime

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
        internal fun activate(stream: DynamicSourceProducer<CryptoStock>) = Active(id, currencies, SubscribedCurrenciesStream(currencies, stream))
        fun changeCurrencies(newCurrencies: List<String>) = Pending(id, newCurrencies, since)
    }
}

class SubscribedCurrenciesStream(val currencies: List<String>, val stream: DynamicSourceProducer<CryptoStock>) : Publisher<CryptoStock> by stream
