package com.baidaidai.anycloud

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.baidaidai.anycloud.ui.screen.StartScreenContainer
import com.baidaidai.anycloud.ui.theme.AnyCloudTheme
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class AnyCloudApplication : Application()

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AnyCloudTheme {
                StartScreenContainer()
            }
        }

        // Registration notification permission
        val channelID = this.getString(R.string.notification_channel_id)
        val name = this.getString(R.string.notification_channel_name)
        val descriptionText = ""
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel(channelID ,name, importance).apply {
            description = descriptionText
        }
        // Register the channel with the system.
        val notificationManager: NotificationManager =
            this.getSystemService(NotificationManager::class.java) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}
