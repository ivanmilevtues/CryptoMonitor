package com.monitorcrypto.cryptomonitor

import com.monitorcrypto.cryptomonitor.android.message.AndroidSocketHandler
import com.monitorcrypto.cryptomonitor.cache.services.CryptoSocketService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.web.socket.config.annotation.EnableWebSocket
import org.springframework.web.socket.config.annotation.WebSocketConfigurer
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry

@Configuration
@EnableWebSocket
class WebSocketConfiguration : WebSocketConfigurer {

    @Autowired
    private lateinit var cryptoSocketService: CryptoSocketService

    override fun registerWebSocketHandlers(registry: WebSocketHandlerRegistry) {
        registry.addHandler(AndroidSocketHandler(cryptoSocketService.cryptoWebSocketClient), "/websocket")
    }
}