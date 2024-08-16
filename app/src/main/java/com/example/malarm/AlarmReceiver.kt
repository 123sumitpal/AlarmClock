package com.example.malarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        Toast.makeText(context, "Alarm Triggered!", Toast.LENGTH_LONG).show()
        // Here you can add code to handle the alarm trigger event (e.g., showing a notification)
    }
}
