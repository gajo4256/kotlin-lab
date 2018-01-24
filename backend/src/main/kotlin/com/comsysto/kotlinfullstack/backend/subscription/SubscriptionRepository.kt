package com.comsysto.kotlinfullstack.backend.subscription

internal class SubscriptionRepository : MutableMap<String, CurrencyStockSubscription> by mutableMapOf()