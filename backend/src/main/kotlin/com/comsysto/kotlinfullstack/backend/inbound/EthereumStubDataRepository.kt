package com.comsysto.kotlinfullstack.backend.inbound

import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.reactor.flux
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import java.math.BigDecimal

@Component
class EthereumStubDataRepository(
        @Value("\${inbound.stubs.throttle.eth}") val throttleValue: Long) : CurrencyDataRepository {

    override fun currencyKey(): String {
        return "ETH"
    }

    override fun dataStream(): Flux<BigDecimal> {
        return flux {
            while (true) {
                delay(throttleValue)
                val result = BigDecimal(Math.random() * 10000)
                send(result)
            }
        }
    }
}
