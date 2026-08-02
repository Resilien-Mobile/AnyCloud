package com.baidaidai.anycloud.data.notification.liveupdate.mapper

import com.baidaidai.anycloud.data.notification.liveupdate.database.LiveUpdateNotificationEntity
import com.baidaidai.anycloud.domain.notification.model.NotificationConfig

fun LiveUpdateNotificationEntity.toNotificationConfig(): NotificationConfig {
    return NotificationConfig(
        unixTimeStamp = unixTimeStamp,
        notificationTitle = notificationTitle,
        notificationContent = notificationContent,
        notificationID = notificationID,
        isTaskFinished = isTaskFinished
    )
}

fun NotificationConfig.toLiveUpdateNotificationEntity(): LiveUpdateNotificationEntity {
    return LiveUpdateNotificationEntity(
        unixTimeStamp = unixTimeStamp,
        notificationTitle = notificationTitle,
        notificationContent = notificationContent,
        notificationID = notificationID,
        isTaskFinished = isTaskFinished
    )
}
