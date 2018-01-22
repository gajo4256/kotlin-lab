package com.comsysto.kotlinfullstack.backend

import com.comsysto.kotlinfullstack.backend.inbound.CurrencyDataRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import java.time.ZonedDateTime

@Service
class CryptoStockService constructor(dataRepositories: List<CurrencyDataRepository>) {

    val inboundRepos: Map<String, CurrencyDataRepository> = dataRepositories.map { Pair(it.currencyKey(), it) }.toMap()

    fun currentPriceStream(currencyKeys: List<String>): Flux<CryptoStock> {
        return Flux.merge(currencyKeys.map {
            inboundRepos
                    .getOrElse(it, { throw RuntimeException("unknown currencyKey $it") })
                    .dataStream()
                    .map { number ->
                        CryptoStock(it, ZonedDateTime.now(), number)
                    }
        })
    }

}