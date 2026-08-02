package com.baidaidai.anycloud.application.notification.liveupdate

import android.Manifest
import androidx.annotation.RequiresPermission
import com.baidaidai.anycloud.data.notification.liveupdate.gateway.LiveUpdateNotificationGatewayImpl
import javax.inject.Inject

class EnablePowerCloudNotificationUseCase @Inject constructor(
    private val liveUpdateNotificationGatewayImpl: LiveUpdateNotificationGatewayImpl
) {
    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    operator fun invoke(
        notificationTitle: String,
        notificationContent: String,
        notificationID: Int = 10001
    ) {
        liveUpdateNotificationGatewayImpl.pushOnePowerCloudLiveActivity(
            notificationTitle = notificationTitle,
            notificationContent = notificationContent,
            notificationID = notificationID
        )
    }
}