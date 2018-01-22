package com.comsysto.kotlinfullstack

import com.comsysto.kotlinfullstack.backend.RouteHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
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
    fun client() : WebClient {
        return WebClient.create("https://api.coinbase.com/v2/")
    }
}
