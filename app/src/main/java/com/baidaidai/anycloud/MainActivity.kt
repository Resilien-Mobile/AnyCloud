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

        // Registration PowerCloud Live Update Notification Permission
        val powerCloudChannelID = this.getString(R.string.powercloud_liveupdate_notification_channel_id)
        val powerCloudName = this.getString(R.string.powercloud_liveupdate_notification_channel_name)
        val powerCloudDescriptionText = "Enable PowerCloud LiveUpdate"
        val powerCloudImportance = NotificationManager.IMPORTANCE_HIGH
        val powerCloudLiveUpdateChannel = NotificationChannel(powerCloudChannelID ,powerCloudName, powerCloudImportance).apply {
            description = powerCloudDescriptionText
        }

        // Registration AnyCloud Ongoing Notification Permission
        val anyCloudOngoingChannelID = this.getString(R.string.anycloud_ongoing_notification_channel_id)
        val anyCloudOngoingName = this.getString(R.string.anycloud_ongoing_notification_channel_name)
        val anyCloudOngoingDescriptionText = "Enable AnyCloud Ongoing Notification"
        val anyCloudOngoingImportance = NotificationManager.IMPORTANCE_HIGH
        val anyCloudOngoingChannel = NotificationChannel(anyCloudOngoingChannelID ,anyCloudOngoingName, anyCloudOngoingImportance).apply {
            description = anyCloudOngoingDescriptionText
        }

        // Registration AnyCloud Live Update Notification Permission
        val anyCloudLiveUpdateChannelID = this.getString(R.string.anycloud_liveupdate_notification_channel_id)
        val anyCloudLiveUpdateName = this.getString(R.string.anycloud_liveupdate_notification_channel_name)
        val anyCloudLiveUpdateDescriptionText = "Enable AnyCloud LiveUpdate Notification"
        val anyCloudLiveUpdateImportance = NotificationManager.IMPORTANCE_HIGH
        val anyCloudLiveUpdateChannel = NotificationChannel(anyCloudLiveUpdateChannelID ,anyCloudLiveUpdateName, anyCloudLiveUpdateImportance).apply {
            description = anyCloudLiveUpdateDescriptionText
        }

        // Register the channel with the system.
        val notificationManager: NotificationManager =
            this.getSystemService(NotificationManager::class.java) as NotificationManager

        notificationManager.createNotificationChannel(powerCloudLiveUpdateChannel)
        notificationManager.createNotificationChannel(anyCloudOngoingChannel)
        notificationManager.createNotificationChannel(anyCloudLiveUpdateChannel)
    }
}
