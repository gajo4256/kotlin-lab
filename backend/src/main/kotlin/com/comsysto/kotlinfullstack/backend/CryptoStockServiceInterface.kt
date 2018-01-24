package com.comsysto.kotlinfullstack.backend

import com.comsysto.kotlinfullstack.CryptoStock
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface CryptoStockServiceInterface {
    fun currentPriceStream(currencyKeys: List<String>): Flux<CryptoStock>
    fun getAvailableCurrencies(): Mono<List<String>>
}