package com.comsysto.kotlinfullstack.api.crypto

import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Component
class ReactiveClient constructor(val client: WebClient) {

    fun getCurrencies(): Mono<CurrencyMetaDataResponse> {
        return client
            .get()
            .uri("currencies")
            .retrieve()
            .bodyToMono(CurrencyMetaDataResponse::class.java)
            .log()
    }

    fun getExchangeRatesFor(currency: String? = "USD"): Flux<ExchangeRateMetaDataResponse> {
        return client
            .get()
            .uri("exchange-rates?currency=$currency")
            .retrieve()
            .bodyToFlux(ExchangeRateMetaDataResponse::class.java)
            .log()
    }

    /**
     * Get the current market price for coin. This is usually somewhere in between the buy and sell price.
     */
    fun getSpotPrice(cryptoCurrency: String, currency: String? = "USD"): Flux<SpotPriceMetaDataResponse> {
        return client
            .get()
            .uri("prices/$cryptoCurrency-$currency/spot")
            .retrieve()
            .bodyToFlux(SpotPriceMetaDataResponse::class.java)
            .log()
    }

}
