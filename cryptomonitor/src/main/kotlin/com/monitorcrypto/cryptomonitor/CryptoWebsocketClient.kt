package com.monitorcrypto.cryptomonitor

import com.google.gson.GsonBuilder
import com.google.gson.annotations.SerializedName
import org.java_websocket.client.WebSocketClient
import org.java_websocket.handshake.ServerHandshake
import java.math.BigDecimal
import java.net.URI


class CryptoWebSocketClient(private val url: URI) : WebSocketClient(url) {
    private val gson = GsonBuilder().setPrettyPrinting().create()

    override fun onOpen(handshake: ServerHandshake?) {
        println("Connection initiated OnOpen called")
        send("{\"action\": \"SubAdd\",\"subs\": [\"0~Coinbase~BTC~USD\"]}")
    }

    override fun onMessage(message: String?) {
        try {
            val crypto = gson.fromJson(message, SocketResponse::class.java)
            if (crypto.type == "0") {
                println(crypto)
            }
        } catch (e: Exception) {
            println(e)
        }
        println(message)
    }

    override fun onClose(p0: Int, p1: String?, p2: Boolean) {
        println("OnClose called for some reason?!")
    }

    override fun onError(ex: Exception?) {
        println(ex)
    }

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