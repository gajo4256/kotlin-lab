package com.comsysto.kotlinfullstack.backend

import reactor.core.publisher.Flux

class CachingCryptoStockServiceDecorator(private val subject: CryptoStockServiceInterface) : CryptoStockServiceInterface by subject {
    private val streamMap: MutableMap<List<String>, Flux<CryptoStock>> = mutableMapOf()

    override fun currentPriceStream(currencyKeys: List<String>): Flux<CryptoStock> {
        val sortedKeys = currencyKeys.sorted()
        if (!streamMap.containsKey(sortedKeys)) {
            streamMap[sortedKeys] = subject.currentPriceStream(sortedKeys)
        }

        return streamMap[sortedKeys]!!
    }
}