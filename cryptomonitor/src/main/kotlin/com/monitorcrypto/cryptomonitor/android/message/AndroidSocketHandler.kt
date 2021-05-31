package com.monitorcrypto.cryptomonitor.android.message

import com.monitorcrypto.cryptomonitor.CryptoWebSocketClient
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.handler.AbstractWebSocketHandler

class AndroidSocketHandler(private val cryptoWebSocketClient: CryptoWebSocketClient?) : AbstractWebSocketHandler() {

    override fun handleTextMessage(session: WebSocketSession, message: TextMessage) {
        val cryptoKey: String = message.payload
        println("$cryptoKey received added listener")
        if (cryptoKey.contains("closed")) {
            cryptoWebSocketClient?.remove(cryptoKey.split(" ")[0], session)
        } else {
            cryptoWebSocketClient?.addListener(cryptoKey, session) ?: println("WebSocket client is null")
        }
    }
}