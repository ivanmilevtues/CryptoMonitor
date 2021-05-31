package com.ivan.cryptomonitor.sockets

import com.ivan.cryptomonitor.dto.CryptoDto
import tech.gusavila92.websocketclient.WebSocketClient
import java.lang.Exception
import java.net.URI

class CryptoWebSocket(subscriptionUrl:URI, private val cryptoKey: String, private val updatableActivity: UpdatableActivity): WebSocketClient(subscriptionUrl) {
    override fun onOpen() {
        send(cryptoKey);
    }

    override fun onTextReceived(message: String?) {
        val cryptoData = CryptoDto.fromJson(message!!)
        println(cryptoData)
        updatableActivity.update(cryptoData)
    }

    override fun onBinaryReceived(data: ByteArray?) {
        println("Message received byt binary?")
    }

    override fun onPingReceived(data: ByteArray?) {
        println("On ping")
    }

    override fun onPongReceived(data: ByteArray?) {
        println("On pong")
    }

    override fun onException(e: Exception?) {
        println(e?.message)
    }

    override fun onCloseReceived() {
        send("$cryptoKey cancel")
    }
}