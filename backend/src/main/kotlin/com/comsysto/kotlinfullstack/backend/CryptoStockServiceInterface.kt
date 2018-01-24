package com.comsysto.kotlinfullstack.backend

import org.reactivestreams.Publisher
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface CryptoStockServiceInterface {
    fun currentPriceStream(currencyKeys: List<String>): Publisher<CryptoStock>
    fun getAvailableCurrencies(): Mono<List<String>>
}