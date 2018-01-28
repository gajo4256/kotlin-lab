package com.comsysto.kotlinfullstack.backend

import com.comsysto.kotlinfullstack.CryptoStock
import org.reactivestreams.Publisher
import reactor.core.publisher.Flux
import java.time.Duration

class MonitoringStockServiceDecorator(private val service: CryptoStockServiceInterface) : CryptoStockServiceInterface by service {
    private var throughput = 0

    private var count = 0

    init {
        Flux.interval(Duration.ofSeconds(1)).subscribe {
            throughput = count
            count = 0
        }
    }

    override fun currentPriceStream(currencyKeys: List<String>): Publisher<CryptoStock> {
        val currentPriceStream = Flux.from(service.currentPriceStream(currencyKeys))
        return currentPriceStream.doOnNext {
            count++
        }

    }

    fun throughput(): Int {
        return throughput
    }
}
