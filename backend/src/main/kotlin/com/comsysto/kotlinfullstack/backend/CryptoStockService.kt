package com.comsysto.kotlinfullstack.backend

import com.comsysto.kotlinfullstack.backend.inbound.CurrencyDataRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import java.time.ZonedDateTime

@Service
class CryptoStockService constructor(dataRepositories: List<CurrencyDataRepository>) {

    val inboundRepos: Map<String, CurrencyDataRepository> = dataRepositories.map { Pair(it.currencyKey(), it) }.toMap()

    fun currentPriceStream(currencyKey: String): Flux<CryptoStock> {

        val dataRepository = inboundRepos.getOrElse(currencyKey, { throw RuntimeException("unknown currencyKey $currencyKey") })

        return Flux.from(dataRepository.dataStream().map {
            CryptoStock(currencyKey, ZonedDateTime.now(), it)
        })
    }

}