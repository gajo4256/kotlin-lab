package com.comsysto.kotlinfullstack.api.crypto

data class CurrencyMetaDataResponse(val data: List<CurrencyMetaData>)

data class CurrencyMetaData(val id: String, val min_size: String, val name: String)

data class ExchangeRateMetaDataResponse(val data: ExchangeRate)

data class ExchangeRate(val currency: String, val rates: Map<String, String>)
