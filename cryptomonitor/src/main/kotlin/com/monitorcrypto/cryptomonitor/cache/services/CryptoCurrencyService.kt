package com.monitorcrypto.cryptomonitor.cache.services

import com.monitorcrypto.cryptomonitor.SocketResponse
import com.monitorcrypto.cryptomonitor.cache.models.CryptoCurrency
import com.monitorcrypto.cryptomonitor.cache.repositories.CryptoCurrencyRepository
import java.util.*

class CryptoCurrencyService(private val cryptoCurrencyRepository: CryptoCurrencyRepository) {

    fun getCurrencyAfter(currencyName: String, afterDate: Date) =
        cryptoCurrencyRepository.findAllByCryptoCurrencyNameAfter(currencyName, afterDate)

    fun addCurrency(currency: SocketResponse) = cryptoCurrencyRepository.save(
        CryptoCurrency(
            currency.market,
            currency.cryptoCurrencyName,
            currency.fiatMoneyName,
            currency.price,
            Date(currency.transactionTimeUTC)
        )
    )
}