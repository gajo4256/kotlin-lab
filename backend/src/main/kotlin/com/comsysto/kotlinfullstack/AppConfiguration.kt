package com.comsysto.kotlinfullstack

import com.comsysto.kotlinfullstack.backend.CachingCryptoStockServiceDecorator
import com.comsysto.kotlinfullstack.backend.CryptoStockService
import com.comsysto.kotlinfullstack.backend.CryptoStockServiceInterface
import com.comsysto.kotlinfullstack.backend.RouteHandler
import com.comsysto.kotlinfullstack.backend.inbound.CurrencyDataRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.server.router


@Configuration
class AppConfiguration {
    @Bean
    fun routes(routeHandler: RouteHandler) = router {
        "/stocks".nest {
            GET("/", routeHandler::cryptoStockTicker)
        }
    }

    @Bean
    fun client(): WebClient {
        return WebClient.create("https://api.coinbase.com/v2/")
    }

    @Bean
    fun cryptoStockService(repos: List<CurrencyDataRepository>): CryptoStockService = CryptoStockService(repos)

    @Bean
    fun cryptoStockServiceCaching(service: CryptoStockService): CryptoStockServiceInterface = CachingCryptoStockServiceDecorator(service)
}
