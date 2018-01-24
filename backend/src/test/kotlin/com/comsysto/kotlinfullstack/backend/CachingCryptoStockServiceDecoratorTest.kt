package com.comsysto.kotlinfullstack.backend

import assertk.assert
import assertk.assertions.isEqualTo
import kotlinx.coroutines.experimental.reactor.flux
import org.junit.Test
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.test.StepVerifier
import java.math.BigDecimal
import java.time.ZonedDateTime

class CachingCryptoStockServiceDecoratorTest {

    private var service: CryptoStockServiceInterface = object : CryptoStockServiceInterface {
        override fun currentPriceStream(currencyKeys: List<String>): Flux<CryptoStock> = flux {
            while (true) {
                send(CryptoStock("narf", ZonedDateTime.now(), BigDecimal.valueOf(Math.random())))
            }
        }

        override fun getAvailableCurrencies(): Mono<List<String>> {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }
    }

    private var testee: CachingCryptoStockServiceDecorator = CachingCryptoStockServiceDecorator(service)

    @Test
    fun currentPriceStream() {
        val currencyKeys = listOf("a", "b")
        val permutedCurrencyKeys = listOf("b", "a")

        var firstSubscription = emptyList<CryptoStock>()
        var secondSubscription = emptyList<CryptoStock>()


        StepVerifier.create(Flux.from(testee.currentPriceStream(currencyKeys)).take(3))
                .consumeNextWith {
                    Flux.from(testee.currentPriceStream(permutedCurrencyKeys)).take(2).subscribe { collected ->
                        secondSubscription += collected
                    }
                }
                .consumeNextWith {
                    firstSubscription += it
                }
                .consumeNextWith {
                    firstSubscription += it
                }.verifyComplete()

        assert(firstSubscription).isEqualTo(secondSubscription)
    }
}