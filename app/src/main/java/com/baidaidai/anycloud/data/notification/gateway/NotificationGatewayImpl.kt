package com.baidaidai.anycloud.data.notification.gateway

import android.content.Context
import androidx.compose.ui.res.stringResource
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.baidaidai.anycloud.R
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class NotificationGatewayImpl @Inject constructor(
    @ApplicationContext context: Context
) {

//    private val notification = NotificationCompat.Builder(context, R.string.notification_channel_id)
//        .setSmallIcon(R.drawable.ic_launcher_foreground)
//        .setContentTitle("AnyCloud")
//        .setContentText("Live update")
//        .setStyle(NotificationCompat.BigTextStyle().bigText("Live update"))
//
//        // Request for promotion
//        .setRequestPromotedOngoing(true)
//        .setOngoing(true)
//        .setPriority(NotificationCompat.PRIORITY_MAX)
//
//        .build()

    private val notificationManager = NotificationManagerCompat.from(context)

}