package com.comsysto.kotlinfullstack.backend

import assertk.assert
import assertk.assertions.isSameAs
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.*
import reactor.core.publisher.Flux

class CachingCryptoStockServiceDecoratorTest {

    private var service: CryptoStockServiceInterface = Mockito.mock(CryptoStockServiceInterface::class.java)
    private var testee: CachingCryptoStockServiceDecorator = CachingCryptoStockServiceDecorator(service)

    @Test
    fun currentPriceStream() {
        val abStream = Flux.just<CryptoStock>()
        val currencyKeys = listOf("a", "b")
        val permutedCurrencyKeys = listOf("b", "a")

        `when`(service.currentPriceStream(currencyKeys)).thenReturn(abStream)

        val stream = testee.currentPriceStream(currencyKeys)
        assert(stream).isSameAs(abStream)
        val stream2 = testee.currentPriceStream(permutedCurrencyKeys)
        assert(stream2).isSameAs(abStream)

        verify(service, times(1)).currentPriceStream(currencyKeys)
    }
}