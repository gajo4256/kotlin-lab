package com.comsysto.kotlinfullstack.backend

import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

@Component
class RouteHandler {
    fun hello(request: ServerRequest): Mono<ServerResponse> {
        val count = request.queryParam("count")
        return count.map {
            ServerResponse.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(Mono.just(Message("hello request #$it")), Message::class.java)
        }.orElse(
                ServerResponse.badRequest()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(Mono.just("missing requets parameter count"), String::class.java)
        )

    }
}