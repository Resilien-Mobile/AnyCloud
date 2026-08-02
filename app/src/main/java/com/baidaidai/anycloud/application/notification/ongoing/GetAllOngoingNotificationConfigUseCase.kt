package com.baidaidai.anycloud.application.notification.ongoing

import com.baidaidai.anycloud.data.notification.ongoing.repository.OngoingNotificationRepositoryImpl
import com.baidaidai.anycloud.domain.notification.model.NotificationConfig
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllOngoingNotificationConfigUseCase @Inject constructor(
    private val ongoingNotificationRepositoryImpl: OngoingNotificationRepositoryImpl
) {
    operator fun invoke(): Flow<List<NotificationConfig>> =
        ongoingNotificationRepositoryImpl.getAllOngoingNotification()
}
