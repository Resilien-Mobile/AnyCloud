package com.baidaidai.anycloud.application.notification

import com.baidaidai.anycloud.data.notification.gateway.NotificationGatewayImpl
import com.baidaidai.anycloud.data.notification.repository.NotificationRepositoryImpl
import com.baidaidai.anycloud.domain.notification.model.NotificationConfig
import javax.inject.Inject

class DeleteOneOngoingNotificationConfigUseCase @Inject constructor(
    private val notificationRepositoryImpl: NotificationRepositoryImpl,
    private val notificationGatewayImpl: NotificationGatewayImpl
) {
    suspend operator fun invoke(notificationConfig: NotificationConfig) {
        // Cancel One Ongoing Notification
        notificationGatewayImpl.cancelOneOngoingNotification(notificationConfig)

        // Delete One Ongoing Notification Config
        notificationRepositoryImpl.deleteOneNotification(notificationConfig)
    }
}
