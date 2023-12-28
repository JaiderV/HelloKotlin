package com.example.hellokotlin

import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.widget.Button
import android.widget.TextView
import androidx.activity.ComponentActivity
import java.util.Locale

class MainActivity : ComponentActivity(), TextToSpeech.OnInitListener {

    private var tts: TextToSpeech? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tts = TextToSpeech(this, this)

        findViewById<Button>(R.id.btnPlay).setOnClickListener { speak() }
    }

    private fun speak() {
        val message: String = findViewById<TextView>(R.id.etMessage).text.toString()

        if (message.isEmpty()) {
            findViewById<TextView>(R.id.tvStatus).text =
                getString(R.string.tts_ifMessage)
            tts?.speak(
                "Antes de presionar Play, tienes que escribir algo",
                TextToSpeech.QUEUE_FLUSH,
                null,
                ""
            )

        } else {
            tts?.speak(message, TextToSpeech.QUEUE_FLUSH, null, "")
            findViewById<TextView>(R.id.tvStatus).text = getString(R.string.tts_elseMessage)
        }
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            findViewById<TextView>(R.id.tvStatus).text = getString(R.string.succes_text)
            tts!!.language = Locale("ES")
        } else {
            findViewById<TextView>(R.id.tvStatus).text = getString(R.string.failure_text)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        tts?.let {
            it.stop()
            it.shutdown()
        }
    }
}