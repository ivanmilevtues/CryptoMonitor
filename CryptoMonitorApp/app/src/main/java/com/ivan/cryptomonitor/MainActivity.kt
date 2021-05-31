package com.ivan.cryptomonitor

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.ivan.cryptomonitor.dto.CryptoDto
import tech.gusavila92.websocketclient.WebSocketClient
import java.lang.Exception
import java.net.URI

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        assignBtnClicks()
    }

    private fun assignBtnClicks() {
        val btcBtn = findViewById<Button>(R.id.BTCBtn)
        val ethBtn = findViewById<Button>(R.id.ETHBtn)
        val xrpBtn = findViewById<Button>(R.id.XRPBtn)
        val eosBtn = findViewById<Button>(R.id.EOSBtn)

        btcBtn.setOnClickListener {
            val intent = Intent(this, CryptoDetails::class.java).apply {
                putExtra("cryptoKey", "BTC")
            }
            startActivity(intent)
        }

        ethBtn.setOnClickListener {
            val intent = Intent(this, CryptoDetails::class.java).apply {
                putExtra("cryptoKey", "ETH")
            }
            startActivity(intent)
        }

        xrpBtn.setOnClickListener {
            val intent = Intent(this, CryptoDetails::class.java).apply {
                putExtra("cryptoKey", "XRP")
            }
            startActivity(intent)
        }

        eosBtn.setOnClickListener {
            val intent = Intent(this, CryptoDetails::class.java).apply {
                putExtra("cryptoKey", "EOS")
            }
            startActivity(intent)
        }
    }

    companion object {
        val subscriptionURL: URI = URI("ws://192.168.1.4:8080/websocket")
    }
}