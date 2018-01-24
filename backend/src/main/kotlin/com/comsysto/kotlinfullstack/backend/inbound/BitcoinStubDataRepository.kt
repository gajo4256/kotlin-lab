package com.comsysto.kotlinfullstack.backend.inbound

import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.reactor.flux
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import java.math.BigDecimal

@Component
class BitcoinStubDataRepository(
        @Value("\${inbound.stubs.throttle.btc}") val throttleValue: Long) : CurrencyDataRepository {
    override fun currencyKey(): String {
        return "BTC"
    }

    override fun dataStream(): Flux<BigDecimal> {
        return flux {
            while (true) {
                delay(throttleValue)
                val result = BigDecimal(Math.random() * 3004)
                send(result)
            }
        }
    }
}
