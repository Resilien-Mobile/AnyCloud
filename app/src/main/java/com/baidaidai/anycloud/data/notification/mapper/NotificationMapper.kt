package com.baidaidai.anycloud.data.notification.mapper

import com.baidaidai.anycloud.data.notification.database.NotificationEntity
import com.baidaidai.anycloud.domain.notification.model.NotificationConfig

fun NotificationEntity.toNotificationConfig(): NotificationConfig {
    return NotificationConfig(
        unixTimeStamp = unixTimeStamp,
        notificationTitle = notificationTitle,
        notificationContent = notificationContent,
        notificationID = notificationID,
        isTaskFinished = isTaskFinished
    )
}

fun NotificationConfig.toNotificationEntity(id: Long = 0L): NotificationEntity {
    return NotificationEntity(
        unixTimeStamp = unixTimeStamp,
        notificationTitle = notificationTitle,
        notificationContent = notificationContent,
        notificationID = notificationID,
        isTaskFinished = isTaskFinished
    )
}
