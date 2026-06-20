package com.baidaidai.anycloud.data.notification.gateway

import android.Manifest
import android.content.Context
import androidx.annotation.RequiresPermission
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.baidaidai.anycloud.R
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class NotificationGatewayImpl @Inject constructor(
    @ApplicationContext context: Context
) {

    private val notificationBuilder = NotificationCompat.Builder(context, context.getString(R.string.notification_channel_id))
    private val notificationManager = NotificationManagerCompat.from(context)

    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    fun pushOneLiveActivity(
        notificationTitle: String,
        notificationContent: String,
        notificationID: Int = 10001
    ){
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

}