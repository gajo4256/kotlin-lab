package com.comsysto.kotlinfullstack.backend

import org.reactivestreams.Publisher
import reactor.core.publisher.Flux

/**
 * TODO remove stream from map as soon as the last subscriber disconnects
 */
class CachingCryptoStockServiceDecorator(private val subject: CryptoStockServiceInterface) : CryptoStockServiceInterface by subject {
    private val streamMap: MutableMap<List<String>, Publisher<CryptoStock>> = mutableMapOf()

    override fun currentPriceStream(currencyKeys: List<String>): Publisher<CryptoStock> =
            streamMap.getOrPut(currencyKeys.sorted()) {
                val publisher = subject.currentPriceStream(currencyKeys)
                //TODO: this turns the flux into a hot observable
                val from: Flux<CryptoStock> = Flux.from(publisher)
                val autoConnect: Publisher<CryptoStock> = from.publish().autoConnect()
                autoConnect
            }
}


