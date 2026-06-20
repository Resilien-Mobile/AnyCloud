package com.baidaidai.anycloud.application.notification

import android.Manifest
import androidx.annotation.RequiresPermission
import com.baidaidai.anycloud.data.notification.gateway.NotificationGatewayImpl
import javax.inject.Inject

class PushLiveActivityUseCase @Inject constructor(
    private val notificationGatewayImpl: NotificationGatewayImpl
) {
    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    operator fun invoke(
        notificationTitle: String,
        notificationContent: String,
        notificationID: Int = 10001
    ) {
        notificationGatewayImpl.pushOneLiveActivity(
            notificationTitle = notificationTitle,
            notificationContent = notificationContent,
            notificationID = notificationID
        )
    }
}
