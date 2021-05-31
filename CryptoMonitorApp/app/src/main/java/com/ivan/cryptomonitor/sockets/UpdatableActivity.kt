package com.ivan.cryptomonitor.sockets

import com.ivan.cryptomonitor.dto.CryptoDto

interface UpdatableActivity {
    fun update(data: CryptoDto)
}