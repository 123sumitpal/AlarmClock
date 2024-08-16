package com.example.malarm

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
class StopWatchActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StopwatchTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    StopwatchScreen()
                }
            }
        }
    }
}

@Composable
fun StopwatchScreen() {
    var isRunning by remember { mutableStateOf(false) }
    var elapsedTime by remember { mutableLongStateOf(0L) }
    val handler = remember { Handler(Looper.getMainLooper()) }
    var timerRunnable by remember { mutableStateOf(Runnable {}) }

    // Timer logic
    DisposableEffect(isRunning) {
        if (isRunning) {
            timerRunnable = object : Runnable {
                override fun run() {
                    elapsedTime += 100 // Update every second
                    handler.postDelayed(this, 100)
                }
            }
            handler.post(timerRunnable)
        } else {
            handler.removeCallbacks(timerRunnable)
        }
        onDispose {
            handler.removeCallbacks(timerRunnable)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Display the elapsed time
        Text(
            text = formatElapsedTime(elapsedTime),
            fontSize = 48.sp,
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Control buttons
        Row {
            Button(onClick = { isRunning = !isRunning }) {
                Text(text = if (isRunning) "Pause" else "Start")
            }
            Spacer(modifier = Modifier.width(16.dp))
            Button(onClick = {
                isRunning = false
                elapsedTime = 0L
            }) {
                Text(text = "Reset")
            }
        }
    }
}

@SuppressLint("DefaultLocale")
fun formatElapsedTime(elapsedTime: Long): String {
    val milliseconds = (elapsedTime % 1000) / 10
    val seconds = (elapsedTime / 1000) % 60
    val minutes = (elapsedTime / (1000 * 60)) % 60
    val hours = (elapsedTime / (1000 * 60 * 60))
    return String.format("%02d:%02d:%02d:%02d", hours, minutes, seconds,milliseconds)

}
