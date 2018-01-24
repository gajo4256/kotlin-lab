package com.comsysto.kotlinfullstack.backend

import com.comsysto.kotlinfullstack.api.model.CryptoStock
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.bodyToServerSentEvents
import reactor.core.publisher.Mono
import java.net.URI
import java.util.*

@Component
class RouteHandler(private val cryptoStockService: CryptoStockServiceInterface,
                   private val subscriptionRepository: SubscriptionRepository) {

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
        return ServerResponse.ok().bodyToServerSentEvents(cryptoStockService.currentPriceStream(currencies))
    }

    fun createSubscription(request: ServerRequest): Mono<ServerResponse> {
        return request.bodyToMono(CreateSubscriptionRequest::class.java).flatMap {
            val uuid = UUID.randomUUID().toString()
            subscriptionRepository.put(uuid, it.currencies)
            ServerResponse.created(URI.create("http://localhost:9090/subscriptions/${uuid}")).syncBody(uuid)
        }
    }

    fun streamSubscription(request: ServerRequest): Mono<ServerResponse> {
        val uuid = request.pathVariable("uuid")
        val currencies = subscriptionRepository.get(uuid).orEmpty()
        return ServerResponse.ok().bodyToServerSentEvents(cryptoStockService.currentPriceStream(currencies))
    }

    fun getCurrencyKeys(request: ServerRequest): Mono<ServerResponse> =
            ServerResponse
                    .ok()
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .body(cryptoStockService.getAvailableCurrencies(), typeRef<List<String>>())
}

class ErrorResponse(val message: String)

data class CreateSubscriptionRequest(val currencies: List<String>)