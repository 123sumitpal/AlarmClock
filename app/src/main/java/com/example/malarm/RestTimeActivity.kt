// RestTimeActivity.kt
package com.example.malarm

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.malarm.ui.theme.MalarmTheme

class RestTimeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MalarmTheme {
                RestTimeScreen { restTime, alertOption ->
                    val resultIntent = Intent().apply {
                        putExtra(
                            "REST_TIME",
                            restTime
                        )
                        putExtra("ALERT_OPTION", alertOption)
                    }
                    setResult(RESULT_OK, resultIntent)
                    finish()
                }
            }
        }
    }
}


// RestTimeScreen.kt
@Composable
fun RestTimeScreen(onSave: (String, String) -> Unit) {
    var hoursInput by remember { mutableStateOf("") }
    var minutesInput by remember { mutableStateOf("") }
    var secondsInput by remember { mutableStateOf("") }
    var alertOption by remember { mutableStateOf("Vibration") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Set Rest Time (hh:mm:ss):")
        Spacer(modifier = Modifier.height(8.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(
                value = hoursInput,
                onValueChange = { hoursInput = it },
                label = { Text("Hours") },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                singleLine = true,
                modifier = Modifier.width(80.dp)
            )
            OutlinedTextField(
                value = minutesInput,
                onValueChange = { minutesInput = it },
                label = { Text("Minutes") },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                singleLine = true,
                modifier = Modifier.width(80.dp)
            )
            OutlinedTextField(
                value = secondsInput,
                onValueChange = { secondsInput = it },
                label = { Text("Seconds") },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                singleLine = true,
                modifier = Modifier.width(80.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text("Select Alert Option:")
        Spacer(modifier = Modifier.height(8.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            RadioButton(
                selected = alertOption == "Vibration",
                onClick = { alertOption = "Vibration" }
            )
            Text("Vibration")
            Spacer(modifier = Modifier.width(16.dp))
            RadioButton(
                selected = alertOption == "Sound",
                onClick = { alertOption = "Sound" }
            )
            Text("Sound")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                val restTime = "${hoursInput.padStart(2, '0')}:${minutesInput.padStart(2, '0')}:${secondsInput.padStart(2, '0')}"
                onSave(restTime, alertOption)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Save")
        }
    }
}
