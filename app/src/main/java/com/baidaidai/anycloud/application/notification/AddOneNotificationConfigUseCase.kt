package com.baidaidai.anycloud.application.notification

import com.baidaidai.anycloud.data.notification.repository.NotificationRepositoryImpl
import com.baidaidai.anycloud.domain.notification.model.NotificationConfig
import javax.inject.Inject
import kotlin.random.Random

class AddOneNotificationConfigUseCase @Inject constructor(
    private val notificationRepositoryImpl: NotificationRepositoryImpl
) {
    suspend operator fun invoke(
        notificationTitle: String,
        notificationContent: String,
        notificationID: Int
    ) {
        val unixTimeStamp = System.currentTimeMillis()
        val notificationConfig = NotificationConfig(
            unixTimeStamp = unixTimeStamp,
            notificationTitle = notificationTitle,
            notificationContent = notificationContent,
            notificationID = notificationID
        )

        notificationRepositoryImpl.addOneNotification(notificationConfig)
    }
}