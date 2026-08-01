package com.baidaidai.anycloud.application.notification.ongoing

import com.baidaidai.anycloud.data.notification.repository.NotificationRepositoryImpl
import com.baidaidai.anycloud.domain.notification.model.NotificationConfig
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllOngoingNotificationConfigUseCase @Inject constructor(
    private val notificationRepositoryImpl: NotificationRepositoryImpl
) {
    operator fun invoke(): Flow<List<NotificationConfig>> = notificationRepositoryImpl.getAllNotification()
}