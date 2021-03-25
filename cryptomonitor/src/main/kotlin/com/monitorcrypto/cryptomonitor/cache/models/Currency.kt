package com.monitorcrypto.cryptomonitor.cache.models

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import org.springframework.data.redis.core.index.Indexed

import java.math.BigDecimal
import java.util.*

@RedisHash("CryptoCurrency")
data class CryptoCurrency(
    val market: String,
    @Indexed val cryptoCurrencyName: String,
    val fiatMoneyName: String,
    val price: BigDecimal,
    val transactionTime: Date // TODO: Check if this is ok, optimally I want to have indexing by names and time stamps
) {
    @get:Id
    var id: String? = null
}