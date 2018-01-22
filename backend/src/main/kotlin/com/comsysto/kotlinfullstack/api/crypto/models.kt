package com.comsysto.kotlinfullstack.api.crypto

data class CurrencyResponse(val data: List<Currency>)

data class Currency(val id: String, val min_size: String, val name: String)
