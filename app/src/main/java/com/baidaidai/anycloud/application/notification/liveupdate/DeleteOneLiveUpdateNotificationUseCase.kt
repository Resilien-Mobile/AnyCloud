package com.baidaidai.anycloud.application.notification.liveupdate

import com.baidaidai.anycloud.data.notification.liveupdate.repository.LiveUpdateNotificationRepositoryImpl
import com.baidaidai.anycloud.domain.notification.model.NotificationConfig
import javax.inject.Inject

class DeleteOneLiveUpdateNotificationUseCase @Inject constructor(
    private val liveUpdateNotificationRepositoryImpl: LiveUpdateNotificationRepositoryImpl
) {
    suspend operator fun invoke(notificationConfig: NotificationConfig) {
        liveUpdateNotificationRepositoryImpl.deleteOneLiveUpdateNotification(notificationConfig)
    }
}
