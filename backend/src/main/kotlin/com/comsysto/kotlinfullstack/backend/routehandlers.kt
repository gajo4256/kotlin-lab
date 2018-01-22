package com.comsysto.kotlinfullstack.backend

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Flux

@Component
class RouteHandler {

    @Autowired
    lateinit var cryptoStockService:CryptoStockService

    fun cryptoStockTicker(request: ServerRequest) = ServerResponse.ok()
            .contentType(MediaType.TEXT_EVENT_STREAM)
            .body(cryptoStockService.currentPriceStream(), CryptoStock::class.java)
}