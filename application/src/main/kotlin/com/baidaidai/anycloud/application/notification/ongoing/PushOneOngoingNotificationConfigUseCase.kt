package com.baidaidai.anycloud.application.notification.ongoing

import android.Manifest
import androidx.annotation.RequiresPermission
import com.baidaidai.anycloud.application.dailycount.SyncTotalDayCountUseCase
import com.baidaidai.anycloud.application.dailycount.SyncTotalPlanCountUseCase
import com.baidaidai.anycloud.application.dailytrack.AddDailyTrackRecordUseCase
import com.baidaidai.anycloud.data.notification.ongoing.gateway.OngoingNotificationGatewayImpl
import com.baidaidai.anycloud.data.notification.ongoing.repository.OngoingNotificationRepositoryImpl
import com.baidaidai.anycloud.domain.notification.model.NotificationConfig
import javax.inject.Inject

class PushOneOngoingNotificationConfigUseCase @Inject constructor(
    private val ongoingNotificationRepositoryImpl: OngoingNotificationRepositoryImpl,
    private val ongoingNotificationGatewayImpl: OngoingNotificationGatewayImpl,
    private val syncTotalPlanCountUseCase: SyncTotalPlanCountUseCase,
    private val syncTotalDayCountUseCase: SyncTotalDayCountUseCase,
    private val addDailyTrackRecordUseCase: AddDailyTrackRecordUseCase
) {
    @Suppress("UNUSED_PARAMETER")
    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    suspend operator fun invoke(
        notificationTitle: String,
        notificationContent: String,
        notificationID: Int = 1000
    ) {
        // Search max NotificationID in database, which between 1001..2000.
        val maxNotificationIDInDataBase = ongoingNotificationRepositoryImpl
            .getMaxOngoingNotificationIDBetween(
                minNotificationID = 1001,
                maxNotificationID = 2000
            )
        val nextNotificationID = if (maxNotificationIDInDataBase == null) 1001 else maxNotificationIDInDataBase + 1

        // Create Notification Config
        val unixTimeStamp = System.currentTimeMillis()
        val notificationConfig = NotificationConfig(
            unixTimeStamp = unixTimeStamp,
            notificationTitle = notificationTitle,
            notificationContent = notificationContent,
            notificationID = nextNotificationID
        )

        // Store Notification Config
        ongoingNotificationRepositoryImpl.addOneOngoingNotification(notificationConfig)

        // Sync Daily Count and Daily Track
        syncTotalPlanCountUseCase()
        syncTotalDayCountUseCase()
        addDailyTrackRecordUseCase()

        // Push Notification by Config
        ongoingNotificationGatewayImpl.pushOneOngoingNotification(
            notificationTitle = notificationTitle,
            notificationContent = notificationContent,
            notificationID = nextNotificationID
        )
    }
}
