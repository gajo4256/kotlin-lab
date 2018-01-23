package com.comsysto.kotlinfullstack.backend

import reactor.core.publisher.Flux

interface CryptoStockServiceInterface {
    fun currentPriceStream(currencyKeys: List<String>): Flux<CryptoStock>
}