package com.comsysto.kotlinfullstack.api.crypto

import assertk.assert
import assertk.assertions.isNotNull
import org.junit.Test
import org.springframework.test.web.reactive.server.WebTestClient

class RealCoinbaseApiTest {

    val webTestClient: WebTestClient = WebTestClient
            .bindToServer()
            .baseUrl("https://api.coinbase.com/v2/")
            .build()

    @Test
    fun `Test real coinbase api currency endpoint`() {
        webTestClient
                .get()
                .uri("currencies")
                .exchange()
                .expectStatus().isOk
                .expectBody()
                .consumeWith({
                    response -> assert(response.responseBody).isNotNull()
                })
    }

    @Test
    fun `Test real coinbase api exchange rate endpoint`() {
        webTestClient
                .get()
                .uri("exchange-rates?currency=USD")
                .exchange()
                .expectStatus().isOk
                .expectBody()
                .consumeWith({
                    response -> assert(response.responseBody).isNotNull()
                })
    }
    
}
