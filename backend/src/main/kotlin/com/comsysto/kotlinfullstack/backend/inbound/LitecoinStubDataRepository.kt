package com.comsysto.kotlinfullstack.backend.inbound

import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.reactor.flux
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import java.math.BigDecimal

@Component
class LitecoinStubDataRepository(
        @Value("\${inbound.stubs.throttle.ltc}") val throttleValue: Long) : CurrencyDataRepository {

    override fun currencyKey(): String {
        return "LTC"
    }

    override fun dataStream(): Flux<BigDecimal> {
        val flux = flux {
            println("constructing datastream for ${currencyKey()}")
            while (true) {
                delay(throttleValue)
                val result = BigDecimal(Math.random() * 4711)
                send(result)
            }
        }
        return flux
    }
}
