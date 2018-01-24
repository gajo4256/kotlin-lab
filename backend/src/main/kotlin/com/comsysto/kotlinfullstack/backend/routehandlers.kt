package com.comsysto.kotlinfullstack.backend

import com.comsysto.kotlinfullstack.backend.subscription.CurrencyStockSubscription
import com.comsysto.kotlinfullstack.backend.subscription.SubscriptionService
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.bodyToServerSentEvents
import reactor.core.publisher.Mono
import java.net.URI

@Component
class RouteHandler(private val cryptoStockService: CryptoStockServiceInterface,
                   private val subscriptionService: SubscriptionService) {

    fun cryptoStockTicker(request: ServerRequest): Mono<ServerResponse> {

        val currencies = request.queryParams().get("currency") ?: emptyList()
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
            val subscription = subscriptionService.createSubscription(it.currencies)
            ServerResponse.created(URI.create("http://localhost:9090/subscriptions/${subscription.id}")).syncBody(subscription.id)
        }
    }

    fun streamSubscription(request: ServerRequest): Mono<ServerResponse> {
        val uuid = request.pathVariable("uuid")
        val subscription = subscriptionService.get(uuid) ?: return ServerResponse.notFound().build()

        val stream = when (subscription) {
            is CurrencyStockSubscription.Active -> subscription.stream
            is CurrencyStockSubscription.Pending ->  {
                val activated = subscriptionService.activateSubscription(subscription)
                activated.stream
            }
        }
        return ServerResponse.ok().bodyToServerSentEvents(stream)
    }

    fun addCurrencyToSubscription(request: ServerRequest): Mono<ServerResponse> {
        val uuid = request.pathVariable("uuid")
        val currency = request.pathVariable("currencyId")

        subscriptionService[uuid] ?: return ServerResponse.notFound().build()
        val sub = subscriptionService[uuid]!!
        subscriptionService.changeSubscription(sub, sub.currencies + currency)

        return ServerResponse.ok().build()
    }

    fun getCurrencyKeys(request: ServerRequest): Mono<ServerResponse> =
            ServerResponse
                    .ok()
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .body(cryptoStockService.getAvailableCurrencies(), typeRef<List<String>>())
}

class ErrorResponse(val message: String)

data class CreateSubscriptionRequest(val currencies: List<String>)