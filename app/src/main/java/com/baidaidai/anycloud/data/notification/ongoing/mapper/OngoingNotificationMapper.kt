package com.baidaidai.anycloud.data.notification.ongoing.mapper

import com.baidaidai.anycloud.data.notification.ongoing.database.OngoingNotificationEntity
import com.baidaidai.anycloud.domain.notification.model.NotificationConfig

fun OngoingNotificationEntity.toNotificationConfig(): NotificationConfig {
    return NotificationConfig(
        unixTimeStamp = unixTimeStamp,
        notificationTitle = notificationTitle,
        notificationContent = notificationContent,
        notificationID = notificationID,
        isTaskFinished = isTaskFinished
    )
}

fun NotificationConfig.toOngoingNotificationEntity(id: Long = 0L): OngoingNotificationEntity {
    return OngoingNotificationEntity(
        unixTimeStamp = unixTimeStamp,
        notificationTitle = notificationTitle,
        notificationContent = notificationContent,
        notificationID = notificationID,
        isTaskFinished = isTaskFinished
    )
}
