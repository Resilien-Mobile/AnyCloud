package com.baidaidai.anycloud.application.notification.ongoing

import com.baidaidai.anycloud.data.notification.ongoing.gateway.OngoingNotificationGatewayImpl
import com.baidaidai.anycloud.data.notification.ongoing.repository.OngoingNotificationRepositoryImpl
import com.baidaidai.anycloud.domain.notification.model.NotificationConfig
import javax.inject.Inject

class DeleteOneOngoingNotificationConfigUseCase @Inject constructor(
    private val ongoingNotificationRepositoryImpl: OngoingNotificationRepositoryImpl,
    private val ongoingNotificationGatewayImpl: OngoingNotificationGatewayImpl
) {
    suspend operator fun invoke(notificationConfig: NotificationConfig) {
        // Cancel One Ongoing Notification
        ongoingNotificationGatewayImpl.cancelOneOngoingNotification(notificationConfig)

        // Delete One Ongoing Notification Config
        ongoingNotificationRepositoryImpl.deleteOneOngoingNotification(notificationConfig)
    }
}
