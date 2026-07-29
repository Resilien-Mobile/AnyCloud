package com.baidaidai.anycloud.application.notification

import android.Manifest
import androidx.annotation.RequiresPermission
import com.baidaidai.anycloud.data.notification.gateway.NotificationGatewayImpl
import com.baidaidai.anycloud.data.notification.repository.NotificationRepositoryImpl
import com.baidaidai.anycloud.domain.notification.model.NotificationConfig
import javax.inject.Inject

class PushOneOngoingNotificationConfigUseCase @Inject constructor(
    private val notificationRepositoryImpl: NotificationRepositoryImpl,
    private val notificationGatewayImpl: NotificationGatewayImpl
) {
    @Suppress("UNUSED_PARAMETER")
    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    suspend operator fun invoke(
        notificationTitle: String,
        notificationContent: String,
        notificationID: Int = 1000
    ) {
        // Search max NotificationID in database, which between 1001..2000.
        val maxNotificationIDInDataBase = notificationRepositoryImpl.getMaxNotificationIDBetween(minNotificationID = 1001, maxNotificationID = 2000)
        val nextNotificationID = if (maxNotificationIDInDataBase == null) 1001 else maxNotificationIDInDataBase + 1

        // Create Notification Config
        val unixTimeStamp = System.currentTimeMillis()
        val notificationConfig = NotificationConfig(
            unixTimeStamp = unixTimeStamp,
            notificationTitle = notificationTitle,
            notificationContent = notificationContent,
            notificationID = nextNotificationID
        )

        // Store Notification Config
        notificationRepositoryImpl.addOneNotification(notificationConfig)

        // Push Notification by Config
        notificationGatewayImpl.pushOneOngoingNotification(
            notificationTitle = notificationTitle,
            notificationContent = notificationContent,
            notificationID = nextNotificationID
        )
    }
}