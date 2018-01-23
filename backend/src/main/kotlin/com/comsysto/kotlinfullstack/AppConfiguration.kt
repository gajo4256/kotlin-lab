package com.comsysto.kotlinfullstack

import com.comsysto.kotlinfullstack.backend.*
import com.comsysto.kotlinfullstack.backend.inbound.CurrencyDataRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.server.router

@EnableScheduling
@Configuration
class AppConfiguration {

    @Autowired
    lateinit var repos: List<CurrencyDataRepository>

    @Bean
    fun routes(routeHandler: RouteHandler) = router {
        "/stocks".nest {
            GET("/", routeHandler::cryptoStockTicker)
        }
        "/config".nest {
            GET ("/currencies", routeHandler::getCurrencyKeys)
        }
    }

    @Bean
    fun client(): WebClient {
        return WebClient.create("https://api.coinbase.com/v2/")
    }

    @Bean
    fun cryptoStockService(): CryptoStockService = CryptoStockService(repos)

    @Bean
    fun cachingCryptoStockService(): CryptoStockServiceInterface = CachingCryptoStockServiceDecorator(cryptoStockService())

    @Bean
    @Primary
    fun meteredCryptoStockService(): MonitoringStockServiceDecorator = MonitoringStockServiceDecorator(cachingCryptoStockService())

    @Bean
    fun meterService(): MeterService = MeterService(meteredCryptoStockService())
}
