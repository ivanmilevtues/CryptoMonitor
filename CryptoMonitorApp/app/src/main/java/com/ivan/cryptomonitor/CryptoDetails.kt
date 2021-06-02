package com.ivan.cryptomonitor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.ivan.cryptomonitor.dto.CryptoDto
import com.ivan.cryptomonitor.sockets.CryptoWebSocket
import com.ivan.cryptomonitor.sockets.UpdatableActivity
import com.jjoe64.graphview.GraphView
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
import java.net.URI

class CryptoDetails : AppCompatActivity(), UpdatableActivity {
    private lateinit var socketClient: CryptoWebSocket
    private var isSocketRunning = false;
    private val series = LineGraphSeries<DataPoint>()

    private lateinit var graphView: GraphView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crypto_details)
        setCryptoIcon()

        initGraph()

        if(!isSocketRunning) {
            initConnection()
            print("Connection OnCreate")
        }
    }

    override fun onResume() {
        super.onResume()
        if(!isSocketRunning) {
            print("Connection OnResume")
            initConnection()
        }
    }

    override fun onPause() {
        super.onPause()
        if(isSocketRunning) {
            closeConnection()
            print("closing for onPause")
        }
    }

    override fun onStop() {
        super.onStop()
        if(isSocketRunning) {
            closeConnection()
            print("closing for onStart")
        }
    }

    override fun update(data: CryptoDto) {
        val cryptoDetails = findViewById<TextView>(R.id.cryptoDescription)
        series.appendData(DataPoint(data.transactionTime.time.toDouble(), data.price.toDouble()), false,22)
        println("${data.transactionTime.time.toDouble()}, ${data.price.toDouble()}")
        cryptoDetails.text = "Last transaction for ${data.cryptoName} was made in ${data.transactionTime} for ${data.price} ${data.fiatMoneyName} on the ${data.market} market."
    }

    private fun setCryptoIcon() {
        val cryptoKey: String = (intent.extras?.get("cryptoKey")) as String
        val cryptoIconView = findViewById<ImageView>(R.id.cryptoIcon)
        when(cryptoKey) {
            "BTC" -> cryptoIconView.setImageResource(R.mipmap.btc_icon_foreground)
            "ETH" -> cryptoIconView.setImageResource(R.mipmap.eth_icon_foreground)
            "EOS" -> cryptoIconView.setImageResource(R.mipmap.eos_icon_foreground)
            "XRP" -> cryptoIconView.setImageResource(R.mipmap.xrp_icon_foreground)
        }
    }

    private fun initConnection() {
        val cryptoKey: String = (intent.extras?.get("cryptoKey")) as String
        socketClient = CryptoWebSocket(subscriptionURL, cryptoKey, this)
        socketClient.setConnectTimeout(10000);
        socketClient.setReadTimeout(60000);
        socketClient.enableAutomaticReconnection(5000);
        socketClient.connect();
        isSocketRunning = true;
    }

    private fun closeConnection() {
        socketClient.send((intent.extras?.get("cryptoKey")) as String + " closed")
        socketClient.close()
        isSocketRunning = false;
    }

    private fun initGraph() {
        graphView = findViewById<GraphView>(R.id.realTimeGraph)
        graphView.addSeries(series)

        graphView.onDataChanged(false, false)
        graphView.viewport.setMinY(.0);
        graphView.viewport.setMaxY(100000.0)
        graphView.viewport.isScrollable = true
        graphView.viewport.setMinX(1622467980 * 1000.0);
        graphView.viewport.setMaxX((1622468484 + 6000) * 1000.0);
    }

    companion object {
        private val subscriptionURL: URI = URI("ws://192.168.1.4:8080/websocket")
    }
}