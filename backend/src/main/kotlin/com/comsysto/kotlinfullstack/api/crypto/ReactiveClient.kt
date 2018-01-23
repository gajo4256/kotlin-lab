package com.comsysto.kotlinfullstack.api.crypto

import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToFlux
import org.springframework.web.reactive.function.client.bodyToMono
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Component
class ReactiveClient constructor(val client: WebClient) {

    fun getCurrencies(): Mono<CurrencyMetaDataResponse> {
        return client
            .get()
            .uri("currencies")
            .retrieve()
            .bodyToMono()
    }

    fun getExchangeRatesFor(currency: String? = "USD"): Flux<ExchangeRateMetaDataResponse> {
        return client
            .get()
            .uri("exchange-rates?currency=$currency")
            .retrieve()
            .bodyToFlux()
    }

}
