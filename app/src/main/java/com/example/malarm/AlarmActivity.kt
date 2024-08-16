package com.example.malarm

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.Surface
import androidx.core.content.pm.ShortcutInfoCompat
import com.example.malarm.ui.theme.MalarmTheme

class AlarmActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MalarmTheme {
                Surface( ) {
                    AlarmScreen()
                }
        }
        }

    }
}