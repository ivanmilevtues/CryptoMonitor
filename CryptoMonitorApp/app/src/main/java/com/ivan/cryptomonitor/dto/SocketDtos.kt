package com.ivan.cryptomonitor.dto

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.readValue
import java.math.BigDecimal
import java.util.*

data class CryptoDto(
    val market: String,
    val cryptoName: String,
    val fiatMoneyName: String,
    val price: BigDecimal,
    val transactionTime: Date
) {
    companion object {
        fun fromJson(json: String): CryptoDto = ObjectMapper().registerModule(KotlinModule()).readValue(json)
    }
}