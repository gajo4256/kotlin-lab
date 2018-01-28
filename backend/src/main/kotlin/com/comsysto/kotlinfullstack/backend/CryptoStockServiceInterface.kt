package com.comsysto.kotlinfullstack.backend

import com.comsysto.kotlinfullstack.CryptoStock
import org.reactivestreams.Publisher
import reactor.core.publisher.Mono

interface CryptoStockServiceInterface {
    fun currentPriceStream(currencyKeys: List<String>): Publisher<CryptoStock>
    fun getAvailableCurrencies(): Mono<List<String>>
}