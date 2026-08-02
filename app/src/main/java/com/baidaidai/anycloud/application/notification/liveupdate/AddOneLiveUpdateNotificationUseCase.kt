package com.baidaidai.anycloud.application.notification.liveupdate

import com.baidaidai.anycloud.data.notification.liveupdate.repository.LiveUpdateNotificationRepositoryImpl
import com.baidaidai.anycloud.domain.notification.model.NotificationConfig
import javax.inject.Inject

class AddOneLiveUpdateNotificationUseCase @Inject constructor(
    private val liveUpdateNotificationRepositoryImpl: LiveUpdateNotificationRepositoryImpl
) {

    suspend operator fun invoke(
        notificationTitle: String,
        notificationContent: String,
        notificationID: Int = 6001
    ) {
        val unixTimeStamp = System.currentTimeMillis()
        val notificationConfig = NotificationConfig(
            unixTimeStamp = unixTimeStamp,
            notificationTitle = notificationTitle,
            notificationContent = notificationContent,
            notificationID = notificationID
        )

        liveUpdateNotificationRepositoryImpl.addOneLiveUpdateNotification(notificationConfig)
    }

}
