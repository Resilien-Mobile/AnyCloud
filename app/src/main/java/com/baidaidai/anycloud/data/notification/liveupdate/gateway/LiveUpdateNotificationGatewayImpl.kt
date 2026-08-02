package com.baidaidai.anycloud.data.notification.liveupdate.gateway

import android.Manifest
import android.content.Context
import androidx.annotation.RequiresPermission
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.baidaidai.anycloud.R
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LiveUpdateNotificationGatewayImpl @Inject constructor(
    @ApplicationContext private val context: Context
) {

    private val notificationManager = NotificationManagerCompat.from(context)


    // Push
    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    fun pushOnePowerCloudLiveActivity(
        notificationTitle: String,
        notificationContent: String,
        notificationID: Int = 10001
    ){

        val notificationBuilder = NotificationCompat.Builder(context, context.getString(R.string.powercloud_liveupdate_notification_channel_id))

        val notification = notificationBuilder
            .setSmallIcon(R.drawable.material_symbols_cloud)
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
