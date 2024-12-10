package com.bigint.broadcasts.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.wifi.WifiManager

class InternetChangeReceiver(
    private val onInternetStatusChanged: (Boolean) -> Unit
) : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val wifiState =
            intent?.getIntExtra(WifiManager.EXTRA_WIFI_STATE, WifiManager.WIFI_STATE_UNKNOWN)

        when (wifiState) {
            WifiManager.WIFI_STATE_DISABLED -> {
                println("// Wi-Fi is disabled")
                onInternetStatusChanged(false)
            }

            WifiManager.WIFI_STATE_ENABLED -> {
                println(" // Wi-Fi is enabled")
                onInternetStatusChanged(true)
            }

            WifiManager.WIFI_STATE_ENABLING -> {
                println("// Wi-Fi is being enabled")
                // Optional: handle this state
            }

            WifiManager.WIFI_STATE_DISABLING -> {
                println("// Wi-Fi is being disabled")
                // Optional: handle this state
            }

            WifiManager.WIFI_STATE_UNKNOWN -> {
                println("// Wi-Fi state is unknown")
                // Optional: handle this state
            }

            else -> {
                println("// Optional: handle unexpected cases")
            }
        }
    }
}
