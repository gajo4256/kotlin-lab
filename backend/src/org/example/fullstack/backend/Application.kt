package org.example.fullstack.backend

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.router

@SpringBootApplication
class ReactiveApplication(val routeHandler: RouteHandler) {
    @Bean
    fun routes() = router {
        "/hello".nest {
            accept(MediaType.APPLICATION_JSON).nest {
                GET("/", routeHandler::hello)

            }
        }
    }
}

fun main(args: Array<String>) {
    SpringApplication.run(ReactiveApplication::class.java, *args)
}