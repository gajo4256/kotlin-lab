package org.example.fullstack.backend

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse.ok
import org.springframework.web.reactive.function.server.router
import reactor.core.publisher.Mono

@SpringBootApplication
class ReactiveApplication(val routeHandler: RouteHandler) {
    @Bean
    fun routes() = router {
        "/hello".nest {
            GET("/", routeHandler::hello)
        }
    }
}


@Component
class RouteHandler {
    fun hello(request: ServerRequest) = ok()
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .body(Mono.just("Hello World"), String::class.java)
}

fun main(args: Array<String>) {
    SpringApplication.run(ReactiveApplication::class.java, *args)
}