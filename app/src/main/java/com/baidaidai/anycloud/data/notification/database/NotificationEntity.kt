package com.baidaidai.anycloud.data.notification.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class NotificationEntity(
    @PrimaryKey
    val unixTimeStamp: Long,

    val notificationTitle: String,
    val notificationContent: String,
    val notificationID: Int,
    val isTaskFinished: Boolean = false
)
