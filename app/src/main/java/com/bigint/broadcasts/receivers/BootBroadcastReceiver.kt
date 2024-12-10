package com.bigint.broadcasts.receivers

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat

class BootBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == Intent.ACTION_BOOT_COMPLETED) {
            // Handle the boot completed event
            Log.d("BootBroadcastReceiver", "onReceive: BootBroadcastReceiver")
            Toast.makeText(context, "Sample App - Phone Booted", Toast.LENGTH_LONG).show()
            context?.let { showNotification(context = it) }
        }
    }

    private fun showNotification(context: Context) {
        val notificationId = 1001
        val channelId = "boot_notification_channel"

        // Build the notification
        val notification = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(android.R.drawable.ic_dialog_info) // Replace with your app icon
            .setContentTitle("Phone Booted")
            .setContentText("Your phone has successfully booted up.")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        // Display the notification
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(notificationId, notification)
    }
}