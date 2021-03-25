package com.monitorcrypto.cryptomonitor.cache.repositories

import com.monitorcrypto.cryptomonitor.cache.models.CryptoCurrency
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface CryptoCurrencyRepository: CrudRepository<CryptoCurrency, String> {
    fun findAllByCryptoCurrencyNameAfter(currencyName: String, afterDate: Date): List<CryptoCurrency>
}