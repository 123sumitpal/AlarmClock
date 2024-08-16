package com.example.malarm

import android.annotation.SuppressLint
import androidx.compose.ui.platform.LocalContext
import com.example.malarm.ui.theme.MalarmTheme



import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.util.*


@Composable
fun AlarmScreen() {
    var hour by remember { mutableStateOf(0) }
    var minute by remember { mutableStateOf(0) }
    val context = LocalContext.current
    var alarmTime by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Alarm Activity", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(16.dp))

        TimePicker(
            label = "Hour",
            value = hour,
            onValueChange = { if (it in 0..23) hour = it }
        )

        TimePicker(
            label = "Minute",
            value = minute,
            onValueChange = { if (it in 0..59) minute = it }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            setAlarm(hour, minute, context)
            alarmTime = String.format("%02d:%02d", hour, minute)
        }) {
            Text(text = "Set Alarm")
        }

        Spacer(modifier = Modifier.height(16.dp))

        alarmTime?.let {
            Text(text = "Alarm set for: $it", style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Composable
fun TimePicker(label: String, value: Int, onValueChange: (Int) -> Unit) {
    Column {
        Text(text = "$label: $value")
        Row {
            Button(onClick = { if (value > 0) onValueChange(value - 1) }) {
                Text("-")
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = { if (value < 59) onValueChange(value + 1) }) {
                Text("+")
            }
        }
    }
}

@SuppressLint("ScheduleExactAlarm")
fun setAlarm(hour: Int, minute: Int, context: Context) {
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    val intent = Intent(context, AlarmReceiver::class.java)
    val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

    val calendar = Calendar.getInstance().apply {
        set(Calendar.HOUR_OF_DAY, hour)
        set(Calendar.MINUTE, minute)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
    }

    alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)

    Toast.makeText(context, "Alarm set for $hour:$minute", Toast.LENGTH_SHORT).show()
}
