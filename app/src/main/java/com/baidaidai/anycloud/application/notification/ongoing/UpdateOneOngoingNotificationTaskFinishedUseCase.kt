package com.baidaidai.anycloud.application.notification.ongoing

import com.baidaidai.anycloud.data.notification.ongoing.repository.OngoingNotificationRepositoryImpl
import javax.inject.Inject

class UpdateOneOngoingNotificationTaskFinishedUseCase @Inject constructor(
    private val ongoingNotificationRepositoryImpl: OngoingNotificationRepositoryImpl
) {
    suspend operator fun invoke(
        unixTimeStamp: Long,
        isTaskFinished: Boolean = false
    ) {
        ongoingNotificationRepositoryImpl.updateOneOngoingNotificationTaskFinished(
            unixTimeStamp = unixTimeStamp,
            isTaskFinished = isTaskFinished
        )
    }
}
