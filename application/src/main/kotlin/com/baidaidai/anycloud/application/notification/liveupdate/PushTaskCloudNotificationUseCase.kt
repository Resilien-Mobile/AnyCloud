package com.baidaidai.anycloud.application.notification.liveupdate

import android.Manifest
import androidx.annotation.RequiresPermission
import com.baidaidai.anycloud.application.setting.ObserveOngoingStyleEnabledUseCase
import com.baidaidai.anycloud.data.notification.liveupdate.gateway.LiveUpdateNotificationGatewayImpl
import com.baidaidai.anycloud.data.notification.liveupdate.repository.LiveUpdateNotificationRepositoryImpl
import com.baidaidai.anycloud.domain.notification.model.LiveUpdateProgressConfig
import com.baidaidai.anycloud.domain.notification.model.NotificationConfig
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class PushTaskCloudNotificationUseCase @Inject constructor(
    private val liveUpdateNotificationRepositoryImpl: LiveUpdateNotificationRepositoryImpl,
    private val liveUpdateNotificationGatewayImpl: LiveUpdateNotificationGatewayImpl,
    private val observeOngoingStyleEnabledUseCase: ObserveOngoingStyleEnabledUseCase
) {
    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    suspend operator fun invoke(
        notificationTitle: String = "AnyCloud",
        notificationID: Int = 6001
    ) {

        val isOngoingStyleEnabled = observeOngoingStyleEnabledUseCase().first()
        val resolvedNotificationTitle = if (isOngoingStyleEnabled) {
            val unfinishedNotificationCount = liveUpdateNotificationRepositoryImpl
                .getUnfinishedLiveUpdateNotificationCount()

            "$unfinishedNotificationCount Tasks"
        } else {
            notificationTitle
        }

        val notificationConfigList = liveUpdateNotificationRepositoryImpl
            .getAllLiveUpdateNotification()
            .first()

        if (notificationConfigList.isEmpty()) {
            liveUpdateNotificationGatewayImpl.cancelOneBasicLiveActivity(notificationID)
            return
        }

        val progressConfig = createProgressConfig(notificationConfigList)
        val notificationContent = notificationConfigList
            .firstOrNull { notificationConfig ->
                !notificationConfig.isTaskFinished
            }
            ?.notificationContent
            ?: "All Done 🎉"

        liveUpdateNotificationGatewayImpl.pushOneBasicLiveActivity(
            notificationTitle = resolvedNotificationTitle,
            notificationContent = notificationContent,
            notificationID = notificationID,
            progressConfig = progressConfig
        )
    }

    private fun createProgressConfig(
        notificationConfigList: List<NotificationConfig>
    ): LiveUpdateProgressConfig {
        val totalListSize = notificationConfigList.size
        val unfinishedListSize = notificationConfigList.count { notificationConfig ->
            !notificationConfig.isTaskFinished
        }
        val finishedListSize = totalListSize - unfinishedListSize
        val progressGap = PROGRESS_MAX / totalListSize
        val progressRemainder = PROGRESS_MAX % totalListSize
        val progressGapList = List(totalListSize) { index ->
            progressGap + if (index < progressRemainder) 1 else 0
        }

        var progressPointPosition = 0
        val progressPointList = progressGapList.map { currentProgressGap ->
            progressPointPosition += currentProgressGap
            progressPointPosition
        }
        val progressCursor = if (finishedListSize == 0) {
            0
        } else {
            progressPointList[finishedListSize - 1]
        }

        val progressConfig = LiveUpdateProgressConfig(
            progressGapList = progressGapList,
            progressPointList = progressPointList,
            progressCursor = progressCursor
        )

        return progressConfig
    }

    private companion object {
        const val PROGRESS_MAX = 100
    }
}
