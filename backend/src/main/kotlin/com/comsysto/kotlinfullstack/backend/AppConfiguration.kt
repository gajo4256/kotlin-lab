package com.comsysto.kotlinfullstack.backend

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.router

@Configuration
class AppConfiguration {
    @Bean
    fun routes(routeHandler: RouteHandler) = router {
        "/hello".nest {
            accept(MediaType.APPLICATION_JSON).nest {
                GET("/", routeHandler::hello)

            }
        }
    }
}