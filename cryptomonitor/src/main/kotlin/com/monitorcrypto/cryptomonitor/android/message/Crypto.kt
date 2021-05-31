package com.monitorcrypto.cryptomonitor.android.message

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import java.math.BigDecimal
import java.util.*

data class CryptoMessage(
    val market: String,
    val cryptoName: String,
    val fiatMoneyName: String,
    val price: BigDecimal,
    val transactionTime: Date
) {
    fun toJson(): String = ObjectMapper().registerModule(KotlinModule()).writeValueAsString(this)
}