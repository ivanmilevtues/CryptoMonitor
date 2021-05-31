package com.monitorcrypto.cryptomonitor

import com.monitorcrypto.cryptomonitor.cache.services.CryptoSocketService
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication


@SpringBootApplication
class CryptoMonitorApplication

fun main(args: Array<String>) {
    val context = runApplication<CryptoMonitorApplication>(*args)
    context.getBean(CryptoSocketService::class.java).tryConnect()
}


