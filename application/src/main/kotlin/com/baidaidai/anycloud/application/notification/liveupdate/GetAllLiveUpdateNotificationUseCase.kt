package com.baidaidai.anycloud.application.notification.liveupdate

import com.baidaidai.anycloud.data.notification.liveupdate.repository.LiveUpdateNotificationRepositoryImpl
import com.baidaidai.anycloud.domain.notification.model.NotificationConfig
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllLiveUpdateNotificationUseCase @Inject constructor(
    private val liveUpdateNotificationRepositoryImpl: LiveUpdateNotificationRepositoryImpl
) {
    operator fun invoke(): Flow<List<NotificationConfig>> {
        val notificationConfigFlow =
            liveUpdateNotificationRepositoryImpl.getAllLiveUpdateNotification()

        return notificationConfigFlow
    }
}
