package com.example.malarm;

import android.app.Activity;
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import com.example.malarm.ui.theme.MalarmTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import java.time.format.TextStyle

class GlassActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MalarmTheme {
                Surface {
                    TimerApp()
                }
            }
        }
    }

    @Composable
    fun TimerApp() {
        var hoursInput by remember { mutableStateOf("") }
        var minutesInput by remember { mutableStateOf("") }
        var secondsInput by remember { mutableStateOf("") }
        var millisecondsInput by remember { mutableStateOf("") }
        var countdownTimeInMillis by remember { mutableStateOf(0L) }
        var timeLeftInMillis by remember { mutableStateOf(0L) }
        var isTimerRunning by remember { mutableStateOf(false) }
        var isPaused by remember { mutableStateOf(false) }

        // Timer logic
        LaunchedEffect(isTimerRunning) {
            if (isTimerRunning) {
                while (timeLeftInMillis > 0) {
                    delay(10) // Wait for 10 milliseconds
                    if (!isPaused) {
                        timeLeftInMillis -= 10
                    }
                }
                if (timeLeftInMillis <= 0) {
                    isTimerRunning = false
                }
            }
        }

        // Helper function to convert time to milliseconds
        fun convertToMillis(hours: Int, minutes: Int, seconds: Int, milliseconds: Int): Long {
            return (hours * 3600 + minutes * 60 + seconds) * 1000L + milliseconds
        }

        // Format time to hh:mm:ss.SSS
        fun formatTime(millis: Long): String {
            val totalSeconds = millis / 1000
            val hours = totalSeconds / 3600
            val minutes = (totalSeconds % 3600) / 60
            val seconds = totalSeconds % 60
            val milliseconds = millis % 1000
            return String.format("%02d:%02d:%02d:%02d", hours, minutes, seconds, milliseconds)
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Set Timer ")
            Spacer(modifier = Modifier.height(8.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = hoursInput,
                    onValueChange = { hoursInput = it },
                    label = { Text("hr") },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    singleLine = true,
                    modifier = Modifier.width(80.dp)
                )
                OutlinedTextField(
                    value = minutesInput,
                    onValueChange = { minutesInput = it },
                    label = { Text("Min") },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    singleLine = true,
                    modifier = Modifier.width(80.dp)
                )
                OutlinedTextField(
                    value = secondsInput,
                    onValueChange = { secondsInput = it },
                    label = { Text("Sec") },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    singleLine = true,
                    modifier = Modifier.width(80.dp)
                )
                OutlinedTextField(
                    value = millisecondsInput,
                    onValueChange = { millisecondsInput = it },
                    label = { Text("ms") },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    singleLine = true,
                    modifier = Modifier.width(80.dp)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(
                    onClick = {
                        val hours = hoursInput.toIntOrNull() ?: 0
                        val minutes = minutesInput.toIntOrNull() ?: 0
                        val seconds = secondsInput.toIntOrNull() ?: 0
                        val milliseconds = millisecondsInput.toIntOrNull() ?: 0
                        countdownTimeInMillis = convertToMillis(hours, minutes, seconds, milliseconds)
                        timeLeftInMillis = countdownTimeInMillis
                        isTimerRunning = true
                        isPaused = false
                    },
                    enabled = !isTimerRunning
                ) {
                    Text("Start Timer")
                }
                Button(
                    onClick = {
                        isPaused = !isPaused // Toggle pause/resume
                    },
                    enabled = isTimerRunning
                ) {
                    Text(if (isPaused) "Resume" else "Pause")
                }
                Button(
                    onClick = {
                        timeLeftInMillis = 0
                        isTimerRunning = false
                        isPaused = false
                    },
                    enabled = timeLeftInMillis > 0
                ) {
                    Text("Reset Timer")
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text("Time left: ${formatTime(timeLeftInMillis)}")
        }
    }
}