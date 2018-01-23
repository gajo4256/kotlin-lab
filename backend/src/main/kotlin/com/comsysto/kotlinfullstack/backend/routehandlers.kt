package com.comsysto.kotlinfullstack.backend

import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

@Component
class RouteHandler(private val cryptoStockService: CryptoStockServiceInterface) {

    fun cryptoStockTicker(request: ServerRequest): Mono<ServerResponse> {

        val currencies = request.queryParams().get("currency")?: emptyList()
        if (currencies.isEmpty()) {
            return ServerResponse.badRequest()
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .body(
                            Mono.just(ErrorResponse("required parameter currency is missing")),
                            ErrorResponse::class.java
                    )
        }
        return ServerResponse.ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(cryptoStockService.currentPriceStream(currencies), CryptoStock::class.java)
    }
}

class ErrorResponse(val message: String)