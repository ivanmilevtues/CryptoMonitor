package com.monitorcrypto.cryptomonitor.cache.services

import com.google.gson.GsonBuilder
import com.monitorcrypto.cryptomonitor.CryptoWebSocketClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.DefaultResourceLoader
import org.springframework.core.io.ResourceLoader
import org.springframework.stereotype.Service
import java.net.URI

@Service
class CryptoSocketService {

    private var isConnectionMade: Boolean = false

    @Autowired
    private lateinit var cryptoCurrencyService: CryptoCurrencyService

    private val data: ApiData
        get() {
            val resourceLoader: ResourceLoader = DefaultResourceLoader()

            val jsonFile = resourceLoader.getResource("api.json").file
            val json = jsonFile.bufferedReader().readLines().joinToString(prefix = "", postfix = "", separator = "")
            return GsonBuilder().create().fromJson(json, ApiData::class.java)
        }

    final var cryptoWebSocketClient: CryptoWebSocketClient = CryptoWebSocketClient(data.uri)
        private set

    fun tryConnect() {
        if (isConnectionMade) {
            println("Connection is already made")
            return
        }
        initWebSocketConnection()
    }

    private fun initWebSocketConnection() {


        // Start the socket listener
        cryptoWebSocketClient.cryptoCurrencyService = cryptoCurrencyService
        cryptoWebSocketClient.connect()

    }

    private data class ApiData(val baseUrl: String, val apiKey: String) {
        val uri
            get() = URI("$baseUrl?apiKey=$apiKey")
    }
}