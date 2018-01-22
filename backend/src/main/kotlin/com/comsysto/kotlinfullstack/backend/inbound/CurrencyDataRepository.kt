package com.comsysto.kotlinfullstack.backend.inbound

import reactor.core.publisher.Flux
import java.math.BigDecimal

interface CurrencyDataRepository {
    fun currencyKey(): String
    fun dataStream(): Flux<BigDecimal>
}