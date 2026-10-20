package com.example.atombergremote

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.hardware.ConsumerIrManager
import android.os.Bundle
import android.view.View
import android.webkit.JavascriptInterface
import android.webkit.WebView
import org.json.JSONObject

class MainActivity : Activity() {
    private var ir: ConsumerIrManager? = null
    private var codes = JSONObject()

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ir = getSystemService(Context.CONSUMER_IR_SERVICE) as? ConsumerIrManager
        codes = try {
            JSONObject(assets.open("codes.json").bufferedReader().use { it.readText() })
        } catch (e: Exception) { JSONObject() }

        val web = WebView(this)
        web.setBackgroundColor(0xFF0A0B0D.toInt())
        web.overScrollMode = View.OVER_SCROLL_NEVER
        web.settings.javaScriptEnabled = true
        web.settings.domStorageEnabled = true
        web.addJavascriptInterface(Bridge(), "AndroidIR")
        setContentView(web)
        web.loadUrl("file:///android_asset/index.html")
    }

    inner class Bridge {
        @JavascriptInterface
        fun send(key: String) {
            val c = codes.optJSONObject(key) ?: return
            val arr = c.optJSONArray("pattern") ?: return
            if (arr.length() == 0) return
            val pattern = IntArray(arr.length()) { arr.getInt(it) }
            val emitter = ir
            if (emitter != null && emitter.hasIrEmitter()) {
                emitter.transmit(c.optInt("freq", 38000), pattern)
            }
        }
    }
}
