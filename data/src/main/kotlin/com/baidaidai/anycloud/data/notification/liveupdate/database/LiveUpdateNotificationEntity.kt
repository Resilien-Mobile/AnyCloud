package com.baidaidai.anycloud.data.notification.liveupdate.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "LiveUpdateNotificationEntity")
data class LiveUpdateNotificationEntity(
    @PrimaryKey
    val unixTimeStamp: Long,

    val notificationTitle: String,
    val notificationContent: String,
    val notificationID: Int,
    val isTaskFinished: Boolean = false
)
