package com.example.nfcreader

import android.app.PendingIntent
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var nfcAdapter: NfcAdapter
    private lateinit var statusText: TextView
    private lateinit var messageText: TextView
    private lateinit var vibrator: Vibrator
    private val handler = Handler(Looper.getMainLooper())

    // Replace this with your actual authorized tag ID
    private val AUTHORIZED_TAG_ID = "your_authorized_tag_id_here"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        statusText = findViewById(R.id.status_text)
        messageText = findViewById(R.id.message_text)
        vibrator = getSystemService(VIBRATOR_SERVICE) as Vibrator

        nfcAdapter = NfcAdapter.getDefaultAdapter(this)

        if (nfcAdapter == null) {
            statusText.text = getString(R.string.nfc_not_supported)
            statusText.setTextColor(Color.RED)
        } else if (!nfcAdapter!!.isEnabled) {
            statusText.text = "NFC is disabled"
            statusText.setTextColor(Color.RED)
        }

        handleIntent(intent)
    }

    override fun onResume() {
        super.onResume()
        if (nfcAdapter != null && nfcAdapter!!.isEnabled) {
            val pendingIntent = PendingIntent.getActivity(
                this, 0,
                Intent(this, javaClass).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP),
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                } else {
                    PendingIntent.FLAG_UPDATE_CURRENT
                }
            )

            val filters = arrayOf(
                IntentFilter(NfcAdapter.ACTION_TECH_DISCOVERED),
                IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED)
            )

            nfcAdapter!!.enableForegroundDispatch(this, pendingIntent, filters, null)
        }

        // Reset to home screen when resuming
        resetHomeScreen()
    }

    override fun onPause() {
        super.onPause()
        if (nfcAdapter != null) {
            nfcAdapter!!.disableForegroundDispatch(this)
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent) {
        if (NfcAdapter.ACTION_TECH_DISCOVERED == intent.action ||
            NfcAdapter.ACTION_TAG_DISCOVERED == intent.action
        ) {
            val tag = intent.getParcelableExtra<Tag>(NfcAdapter.EXTRA_TAG)
            if (tag != null) {
                val tagId = tag.id.toHex()
                Log.d("NFC", "Tag ID: $tagId")

                if (tagId == AUTHORIZED_TAG_ID) {
                    showAuthorizedBadge()
                } else {
                    showUnauthorizedBadge()
                }
            }
        }
    }

    private fun showAuthorizedBadge() {
        statusText.text = getString(R.string.badge_verified)
        statusText.setTextColor(Color.parseColor("#4CAF50")) // Green
        messageText.text = "Welcome!"
        messageText.setTextColor(Color.parseColor("#4CAF50"))

        handler.postDelayed({
            resetHomeScreen()
        }, 3000) // Reset after 3 seconds
    }

    private fun showUnauthorizedBadge() {
        statusText.text = getString(R.string.badge_unverified)
        statusText.setTextColor(Color.RED)
        messageText.text = "Access Denied"
        messageText.setTextColor(Color.RED)

        // Vibrate for 500ms
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            @Suppress("DEPRECATION")
            vibrator.vibrate(500)
        }

        handler.postDelayed({
            resetHomeScreen()
        }, 3000) // Reset after 3 seconds
    }

    private fun resetHomeScreen() {
        statusText.text = getString(R.string.tap_card)
        statusText.setTextColor(resources.getColor(R.color.text_primary, null))
        messageText.text = ""
    }

    private fun ByteArray.toHex(): String =
        joinToString("") { "%02X".format(it) }
}
