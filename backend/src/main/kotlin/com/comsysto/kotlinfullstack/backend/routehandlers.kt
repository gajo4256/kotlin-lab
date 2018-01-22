package com.comsysto.kotlinfullstack.backend

import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse

@Component
class RouteHandler(private val cryptoStockService: CryptoStockService) {

    fun cryptoStockTicker(request: ServerRequest) = ServerResponse.ok()
            .contentType(MediaType.TEXT_EVENT_STREAM)
            .body(cryptoStockService.currentPriceStream(), CryptoStock::class.java)
}