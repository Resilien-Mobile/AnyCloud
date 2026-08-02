package com.baidaidai.anycloud.application.notification.liveupdate

import com.baidaidai.anycloud.data.notification.liveupdate.repository.LiveUpdateNotificationRepositoryImpl
import javax.inject.Inject

class UpdateOneLiveUpdateNotificationTaskFinishedUseCase @Inject constructor(
    private val liveUpdateNotificationRepositoryImpl: LiveUpdateNotificationRepositoryImpl
) {
    suspend operator fun invoke(
        unixTimeStamp: Long,
        isTaskFinished: Boolean = false
    ) {
        liveUpdateNotificationRepositoryImpl.updateOneLiveUpdateNotificationTaskFinished(
            unixTimeStamp = unixTimeStamp,
            isTaskFinished = isTaskFinished
        )
    }
}
