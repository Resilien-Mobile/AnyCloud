package com.baidaidai.anycloud.application.notification

import com.baidaidai.anycloud.data.notification.repository.NotificationRepositoryImpl
import javax.inject.Inject

class UpdateOneOngoingNotificationTaskFinishedUseCase @Inject constructor(
    private val notificationRepositoryImpl: NotificationRepositoryImpl
) {
    suspend operator fun invoke(
        unixTimeStamp: Long,
        isTaskFinished: Boolean = false
    ) {
        notificationRepositoryImpl.updateOneNotificationTaskFinished(
            unixTimeStamp = unixTimeStamp,
            isTaskFinished = isTaskFinished
        )
    }
}
