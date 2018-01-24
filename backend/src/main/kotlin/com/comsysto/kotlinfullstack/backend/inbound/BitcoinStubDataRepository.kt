package com.comsysto.kotlinfullstack.backend.inbound

import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.reactor.flux
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import java.math.BigDecimal

@Component
class BitcoinStubDataRepository : CurrencyDataRepository {
    override fun currencyKey(): String {
        return "BTC"
    }

    override fun dataStream(): Flux<BigDecimal> {
        return flux {
            while (true) {
                delay(1000)
                val result = BigDecimal(Math.random() * 3004)
                send(result)
            }
        }
    }
}
