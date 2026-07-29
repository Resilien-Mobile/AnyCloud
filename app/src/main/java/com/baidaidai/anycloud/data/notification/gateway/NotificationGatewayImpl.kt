package com.baidaidai.anycloud.data.notification.gateway

import android.Manifest
import android.content.Context
import androidx.annotation.RequiresPermission
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.baidaidai.anycloud.R
import com.baidaidai.anycloud.domain.notification.model.NotificationConfig
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class NotificationGatewayImpl @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val notificationManager = NotificationManagerCompat.from(context)

    // Push
    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    fun pushOneLiveActivity(
        notificationTitle: String,
        notificationContent: String,
        notificationID: Int = 10001
    ){

        val notificationBuilder = NotificationCompat.Builder(context, context.getString(R.string.powercloud_liveupdate_notification_channel_id))

        val notification = notificationBuilder
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(notificationTitle)
            .setContentText(notificationContent)

            // Request for promotion
            .setStyle(NotificationCompat.BigTextStyle().bigText(notificationContent))
            .setRequestPromotedOngoing(true)
            .setOngoing(true)
            .setPriority(NotificationCompat.PRIORITY_MAX)

            .build()

        notificationManager.notify(notificationID,notification)

    }

    /**
     * This is NOT A LIVE UPDATE NOTIFICATION !
     */
    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    fun pushOneOngoingNotification(
        notificationTitle: String,
        notificationContent: String,
        notificationID: Int = 1001
    ){

        val notificationBuilder = NotificationCompat.Builder(context, context.getString(R.string.anycloud_ongoing_notification_channel_id))

        val notificationStyle = NotificationCompat.BigTextStyle().bigText(notificationContent)
        val notification = notificationBuilder
            .setSmallIcon(R.drawable.material_symbols_cloud)
            .setContentTitle(notificationTitle)
            .setContentText(notificationContent)

            // Request for promotion
            .setStyle(notificationStyle)
            .setOngoing(true)
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .build()

        notificationManager.notify(notificationID,notification)

    }

    // Cancel
    fun cancelOneOngoingNotification(
        notificationConfig: NotificationConfig
    ){
        notificationManager.cancel(notificationConfig.notificationID)
    }

}