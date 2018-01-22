package com.comsysto.kotlinfullstack.api.crypto

import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToMono
import reactor.core.publisher.Mono

@Component
class ReactiveClient constructor(val client: WebClient) {

    fun getCurrencies(): Mono<CurrencyResponse> {
        return client
            .get()
            .uri("currencies")
            .retrieve()
            .bodyToMono()
    }

}
