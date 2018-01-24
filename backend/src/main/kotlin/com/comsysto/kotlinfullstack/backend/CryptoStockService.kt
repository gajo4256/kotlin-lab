package com.comsysto.kotlinfullstack.backend

import com.comsysto.kotlinfullstack.api.model.CryptoStock
import com.comsysto.kotlinfullstack.backend.inbound.CurrencyDataRepository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.publisher.toMono
import java.time.ZonedDateTime

class CryptoStockService(dataRepositories: List<CurrencyDataRepository>) : CryptoStockServiceInterface {

    private val inboundRepos: Map<String, CurrencyDataRepository> = dataRepositories.map { Pair(it.currencyKey(), it) }.toMap()

    override fun currentPriceStream(currencyKeys: List<String>): Flux<CryptoStock> =
            Flux.merge(currencyKeys.map {
                inboundRepos
                        .getOrElse(it, { throw RuntimeException("unknown currencyKey $it") })
                        .dataStream()
                        .map { number ->
                            CryptoStock(it, ZonedDateTime.now(), number.toDouble())
                        }
            })

    override fun getAvailableCurrencies(): Mono<List<String>> = inboundRepos.keys.toList().toMono()

}