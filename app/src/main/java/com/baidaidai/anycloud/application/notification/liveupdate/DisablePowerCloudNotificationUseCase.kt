package com.baidaidai.anycloud.application.notification.liveupdate

import com.baidaidai.anycloud.data.notification.liveupdate.gateway.LiveUpdateNotificationGatewayImpl
import javax.inject.Inject

class DisablePowerCloudNotificationUseCase @Inject constructor(
    private val liveUpdateNotificationGatewayImpl: LiveUpdateNotificationGatewayImpl
) {
    operator fun invoke(
        notificationId: Int = 10001
    ) {
        liveUpdateNotificationGatewayImpl.disablePowerCloudNotification(
            notificationId = notificationId
        )
    }
}
