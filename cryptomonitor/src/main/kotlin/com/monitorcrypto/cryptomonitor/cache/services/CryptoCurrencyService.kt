package com.monitorcrypto.cryptomonitor.cache.services

import com.monitorcrypto.cryptomonitor.SocketResponse
import com.monitorcrypto.cryptomonitor.cache.models.CryptoCurrency
import com.monitorcrypto.cryptomonitor.cache.repositories.CryptoCurrencyRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class CryptoCurrencyService {

    @Autowired
    private lateinit var cryptoCurrencyRepository: CryptoCurrencyRepository

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