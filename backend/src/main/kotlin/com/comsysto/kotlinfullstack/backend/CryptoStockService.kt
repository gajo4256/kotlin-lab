package com.comsysto.kotlinfullstack.backend

import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import java.math.BigDecimal
import java.time.ZonedDateTime
import kotlin.coroutines.experimental.buildSequence

@Service
class CryptoStockService {

    fun currentPriceStream(): Flux<CryptoStock> {

        val sequence = buildSequence {
            while (true) {
                yield(BigDecimal(Math.random() * 10000))
            }
        }

        return Flux.fromIterable(sequence.asIterable()).map {
            CryptoStock("ETH", ZonedDateTime.now(), it)
        }

    }


}