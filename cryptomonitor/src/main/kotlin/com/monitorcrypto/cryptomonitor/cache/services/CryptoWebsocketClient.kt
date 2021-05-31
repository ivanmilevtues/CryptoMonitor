package com.monitorcrypto.cryptomonitor

import com.google.gson.GsonBuilder
import com.google.gson.annotations.SerializedName
import com.monitorcrypto.cryptomonitor.android.message.CryptoMessage
import com.monitorcrypto.cryptomonitor.cache.services.CryptoCurrencyService
import org.java_websocket.client.WebSocketClient
import org.java_websocket.handshake.ServerHandshake
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession
import java.math.BigDecimal
import java.net.URI
import java.util.*


class CryptoWebSocketClient(url: URI) : WebSocketClient(url) {
    private val gson = GsonBuilder().setPrettyPrinting().create()

    private val listeners = mutableMapOf<String, MutableList<WebSocketSession>>()

    lateinit var cryptoCurrencyService: CryptoCurrencyService

    override fun onOpen(handshake: ServerHandshake?) {
        println("Connection initiated OnOpen called")
        send("{\"action\": \"SubAdd\",\"subs\": [\"0~Coinbase~BTC~USD\", \"0~Coinbase~ETH~USD\", \"0~Coinbase~XRP~USD\", \"0~Coinbase~EOS~USD\"]}")
    }

    override fun onMessage(message: String?) {
        try {
            val socketResponse = gson.fromJson(message, SocketResponse::class.java)
            notifyListeners(socketResponse)
            if (socketResponse.type == "0") {
                cryptoCurrencyService.addCurrency(socketResponse)
            }
        } catch (e: Exception) {
            println(e)
        }
        println(message)
    }

    override fun onClose(p0: Int, p1: String?, p2: Boolean) {
        println("OnClose called.")
    }

    override fun onError(ex: Exception?) {
        println(ex)
    }

    fun addListener(cryptoKey: String, sessionListener: WebSocketSession) {
        if (listeners.containsKey(cryptoKey)) {
            listeners[cryptoKey]?.add(sessionListener)
        } else {
            listeners[cryptoKey] = mutableListOf(sessionListener)
        }
    }

    fun remove(cryptoKey: String, sessionListener: WebSocketSession) {
        if (listeners.containsKey(cryptoKey)) {
            listeners[cryptoKey]?.remove(sessionListener)
        }
    }


    private fun notifyListeners(socketResponse: SocketResponse) {
        if (listeners.containsKey(socketResponse.cryptoCurrencyName)) {
            for (listener in listeners[socketResponse.cryptoCurrencyName]!!) {
                println("Sending data for ${socketResponse.cryptoCurrencyName}...")
                listener.sendMessage(TextMessage(convertResponse(socketResponse).toJson()))
            }
        }
    }


    private fun convertResponse(socketResponse: SocketResponse) =
        CryptoMessage(
            socketResponse.market,
            socketResponse.cryptoCurrencyName,
            socketResponse.fiatMoneyName,
            socketResponse.price,
            Date(socketResponse.transactionTimeUTC * 1000)
        )

}

data class SocketResponse(
    @SerializedName("TYPE") val type: String,
    @SerializedName("M") val market: String,
    @SerializedName("FSYM") val cryptoCurrencyName: String,
    @SerializedName("TSYM") val fiatMoneyName: String,
    @SerializedName("F") val flag: String,
    @SerializedName("ID") val id: String,
    @SerializedName("TS") val transactionTimeUTC: Long,
    @SerializedName("P") val price: BigDecimal
)