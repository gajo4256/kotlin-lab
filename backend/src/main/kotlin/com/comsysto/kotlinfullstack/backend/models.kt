package com.comsysto.kotlinfullstack.backend

import java.math.BigDecimal
import java.time.ZonedDateTime


data class Message(val message: String)

data class CryptoStock(val currency: String, val date: ZonedDateTime, val price: BigDecimal)