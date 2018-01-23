package com.comsysto.kotlinfullstack.backend

import org.springframework.scheduling.annotation.Scheduled
import reactor.core.publisher.Flux

class MonitoringStockServiceDecorator(private val service: CryptoStockServiceInterface) : CryptoStockServiceInterface by service {
    private var throughput = 0

    private var count = 0

    override fun currentPriceStream(currencyKeys: List<String>): Flux<CryptoStock> {
        val currentPriceStream = service.currentPriceStream(currencyKeys)
        return currentPriceStream.doOnNext {
            count++
        }

    }

    @Scheduled(fixedDelay = 1000L)
    fun updateThroughput() {
        throughput = count
        count = 0
    }

    fun throughput(): Int {
        return throughput
    }
}