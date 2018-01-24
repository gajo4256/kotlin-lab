package com.comsysto.kotlinfullstack.backend.inbound

import com.comsysto.kotlinfullstack.api.crypto.ReactiveClient
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import java.math.BigDecimal
import java.time.Duration

@Component
class BitcoinStubDataRepository(private val client: ReactiveClient,
                                @Value("\${inbound.channel.remote.btc}") val throttle: Long) : CurrencyDataRepository {
    
    override fun currencyKey(): String {
        return "BTC"
    }

    override fun dataStream(): Flux<BigDecimal> {
        return client.getSpotPrice(currencyKey())
                .map { BigDecimal(it.data.amount) }
                .delaySubscription(Duration.ofMillis(throttle))
                .repeat()
    }
}
