package com.comsysto.kotlinfullstack.backend.inbound

import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.reactor.flux
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import java.math.BigDecimal

@Component
class LitecoinStubDataRepository : CurrencyDataRepository {

    override fun currencyKey(): String {
        return "LTC"
    }

    override fun dataStream(): Flux<BigDecimal> {
        val flux = flux {
            println("constructing datastream for ${currencyKey()}")
            while (true) {
                delay(2000)
                val result = BigDecimal(Math.random() * 4711)
                send(result)
            }
        }
        return flux
    }
}