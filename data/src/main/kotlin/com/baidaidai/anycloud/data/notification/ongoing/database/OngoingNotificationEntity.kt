package com.baidaidai.anycloud.data.notification.ongoing.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "OngoingNotificationEntity")
data class OngoingNotificationEntity(
    @PrimaryKey
    val unixTimeStamp: Long,

    val notificationTitle: String,
    val notificationContent: String,
    val notificationID: Int,
    val isTaskFinished: Boolean = false
)
