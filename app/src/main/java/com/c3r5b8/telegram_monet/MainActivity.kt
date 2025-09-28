package com.c3r5b8.telegram_monet

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.c3r5b8.telegram_monet.presentation.main_screen.MainScreen
import com.c3r5b8.telegram_monet.ui.theme.TelegramMonetTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            TelegramMonetTheme {
                MainScreen()
            }
        }
    }
}
