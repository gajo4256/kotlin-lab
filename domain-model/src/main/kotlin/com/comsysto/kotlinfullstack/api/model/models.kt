package com.comsysto.kotlinfullstack.api.model

import java.time.ZonedDateTime

data class CryptoStock(val currency: String, val date: ZonedDateTime, val price: Double)