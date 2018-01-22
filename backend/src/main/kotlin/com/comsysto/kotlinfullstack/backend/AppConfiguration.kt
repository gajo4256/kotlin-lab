package com.comsysto.kotlinfullstack.backend

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.router

@Configuration
class AppConfiguration {
    @Bean
    fun routes(routeHandler: RouteHandler) = router {
        "/stocks".nest {
            GET("/", routeHandler::cryptoStockTicker)
        }
    }
}