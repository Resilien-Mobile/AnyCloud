package com.baidaidai.anycloud.application.notification

import com.baidaidai.anycloud.data.notification.repository.NotificationRepositoryImpl
import com.baidaidai.anycloud.domain.notification.model.NotificationConfig
import javax.inject.Inject

class DeleteOneOngoingNotificationConfigUseCase @Inject constructor(
    private val notificationRepositoryImpl: NotificationRepositoryImpl
) {
    suspend operator fun invoke(notificationConfig: NotificationConfig) = notificationRepositoryImpl.deleteOneNotification(notificationConfig)
}
