package com.baidaidai.anycloud.domain.notification.model

data class NotificationConfig(
    val unixTimeStamp: Long = 0L,
    val notificationTitle: String = "AnyCloud",
    val notificationContent: String = "Void",
    val notificationID: Int = 1000,
    val isTaskFinished: Boolean = false
)
