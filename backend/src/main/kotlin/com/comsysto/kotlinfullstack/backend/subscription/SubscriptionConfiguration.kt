package com.comsysto.kotlinfullstack.backend.subscription

import com.comsysto.kotlinfullstack.backend.CryptoStockServiceInterface
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SubscriptionConfiguration {
    @Bean
    fun subscriptionService(serviceInterface: CryptoStockServiceInterface): SubscriptionService = SubscriptionServiceImpl(SubscriptionRepository(), serviceInterface)
}