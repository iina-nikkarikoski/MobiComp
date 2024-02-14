package com.example.myapplication

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class NotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (context != null && intent?.action == "SHOW_NOTIFICATION") {
            val notificationService = NotificationService(context)
            notificationService.showBasicNotification()
        }
    }
}