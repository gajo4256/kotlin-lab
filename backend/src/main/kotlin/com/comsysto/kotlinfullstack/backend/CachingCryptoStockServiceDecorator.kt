package com.comsysto.kotlinfullstack.backend

import com.comsysto.kotlinfullstack.CryptoStock
import org.reactivestreams.Publisher
import reactor.core.publisher.Flux

class CachingCryptoStockServiceDecorator(private val subject: CryptoStockServiceInterface) : CryptoStockServiceInterface by subject {
    private val streamMap: MutableMap<List<String>, Publisher<CryptoStock>> = mutableMapOf()

    override fun currentPriceStream(currencyKeys: List<String>): Publisher<CryptoStock> =
            streamMap.getOrPut(currencyKeys.sorted()) {
                val publisher = subject.currentPriceStream(currencyKeys)
                Flux.from(publisher).publish().refCount()
            }
}


