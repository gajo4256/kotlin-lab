package com.comsysto.kotlinfullstack.backend

import com.comsysto.kotlinfullstack.api.model.CryptoStock
import reactor.core.publisher.Flux

class CachingCryptoStockServiceDecorator(private val subject: CryptoStockServiceInterface) : CryptoStockServiceInterface by subject {
    private val streamMap: MutableMap<List<String>, Flux<CryptoStock>> = mutableMapOf()

    override fun currentPriceStream(currencyKeys: List<String>): Flux<CryptoStock> =
            streamMap.getOrPut(currencyKeys.sorted(), {subject.currentPriceStream(currencyKeys).publish().autoConnect()})
}