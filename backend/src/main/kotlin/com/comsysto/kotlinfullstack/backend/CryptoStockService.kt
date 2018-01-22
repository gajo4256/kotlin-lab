package com.comsysto.kotlinfullstack.backend

import com.comsysto.kotlinfullstack.backend.inbound.EthereumStubDataRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import java.time.ZonedDateTime

@Service
class CryptoStockService constructor(ethereumDataRepository: EthereumStubDataRepository) {

    var ethereumData: EthereumStubDataRepository = ethereumDataRepository

    fun currentPriceStream(currencyKey: String): Flux<CryptoStock> {
        return Flux.from(ethereumData.dataStream()).map {
            CryptoStock(ethereumData.currencyKey(), ZonedDateTime.now(), it)
        }
    }

}