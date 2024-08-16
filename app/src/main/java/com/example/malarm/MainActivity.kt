package com.example.malarm

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.example.malarm.ui.theme.MalarmTheme
import com.example.malarm.ui.theme.NavigationBarColor
import kotlinx.coroutines.delay
import java.util.Calendar

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContent {
            var hour by remember { mutableStateOf(value = "0") }
            var minute by remember { mutableStateOf(value = "0") }
            var second by remember { mutableStateOf(value = "0") }
            var amOrPm by remember { mutableStateOf(value = "0") }

            LaunchedEffect(Unit) {
                while (true) {
                    val cal = Calendar.getInstance()
                    hour = cal.get(Calendar.HOUR).run {
                        if (this.toString().length == 1) "0$this" else "$this"
                    }
                    minute = cal.get(Calendar.MINUTE).run {
                        if (this.toString().length == 1) "0$this" else "$this"
                    }
                    second = cal.get(Calendar.SECOND).run {
                        if (this.toString().length == 1) "0$this" else "$this"
                    }
                    amOrPm = cal.get(Calendar.AM_PM).run {
                        if (this == Calendar.AM) "AM" else "PM"
                    }
                    delay(1000)
                }
            }




            MalarmTheme {
                Scaffold(bottomBar = {
                    NavigationBarComponent(
                        onAlarmClick = {
                            val intent = Intent(this, AlarmActivity::class.java)
                            startActivity(intent)
                        },
                        onHourglassClick = {
                            val intent = Intent(this, GlassActivity::class.java)
                            startActivity(intent)
                        },
                        onStopwatchClick = {
                            val intent = Intent(this, StopWatchActivity::class.java)
                            startActivity(intent)

                        },
                        onHotelClick = {
                            val intent = Intent(this, RestTimeActivity::class.java)
                            startActivity(intent)
                        },

                        )
                })
                {
                    Box(
                        modifier = Modifier
                            .padding(it)
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            HeaderComponent()

                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .fillMaxHeight(fraction = 0.8f),
                                contentAlignment = Alignment.Center
                            ) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    AnalogClockComponent(
                                        hour = hour.toInt(),
                                        minute = minute.toInt(),
                                        second = second.toInt(),
                                    )
//
                                    Spacer(modifier = Modifier.height(24.dp))
                                    DigitalClockComponent(
                                        hour = hour,
                                        minute = minute,
                                        second = second,
                                        amOrPm = amOrPm,
                                    )
                                }
                            }
                        }
                    }

                }
            }

        }
    }

    @Composable
    fun HeaderComponent() {
        Box(modifier = Modifier.padding(vertical = 24.dp)) {
            Text(text = "Clock", style = MaterialTheme.typography.titleMedium)
        }
    }

    @Composable
    fun NavigationBarComponent(
        onAlarmClick: () -> Unit,
        onHourglassClick: () -> Unit,
        onStopwatchClick: () -> Unit,
        onHotelClick: () -> Unit,
    ) {
        NavigationBar(
            containerColor = NavigationBarColor
        ) {
            NavigationBarItem(
                icon = {
                    androidx.compose.material3.Icon(
                        painter = painterResource(id = R.drawable.baseline_access_alarm_24),
                        contentDescription = null
                    )
                },
                selected = false,
                onClick = onAlarmClick
            )
            NavigationBarItem(
                icon = {
                    androidx.compose.material3.Icon(
                        painter = painterResource(id = R.drawable.hourglass__1_),
                        contentDescription = null
                    )
                },
                selected = false, onClick = onHourglassClick
            )
            NavigationBarItem(
                icon = {
                    androidx.compose.material3.Icon(
                        painter = painterResource(id = R.drawable.baseline_access_alarm_24),
                        contentDescription = null
                    )
                },
                selected = true, onClick = {}
            )
            NavigationBarItem(
                icon = {
                    androidx.compose.material3.Icon(
                        painter = painterResource(id = R.drawable.stopwatch_1),
                        contentDescription = null
                    )
                },
                selected = false, onClick = onStopwatchClick
            )
            NavigationBarItem(
                icon = {
                    androidx.compose.material3.Icon(
                        painter = painterResource(id = R.drawable.baseline_hotel_24),
                        contentDescription = null
                    )
                },
                selected = false, onClick = onHotelClick
            )
        }
    }

    @Composable
    fun DigitalClockComponent(
        hour: String,
        minute: String,
        amOrPm: String,
        second: String,
    ) {
        Text(
            text = "$hour:$minute:$second,$amOrPm",
            style = MaterialTheme.typography.titleLarge
        )
        Text(
            text = "Delhi India",
            style = MaterialTheme.typography.bodyMedium.merge(
                TextStyle(
                    color = MaterialTheme.colorScheme.onBackground.copy(
                        alpha = 0.6f
                    )
                )
            )
        )
    }
}




