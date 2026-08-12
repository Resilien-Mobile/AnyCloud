package com.baidaidai.anycloud.data.notification.liveupdate.database

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = LiveUpdateNotificationEntity::class,
            parentColumns = ["unixTimeStamp"],
            childColumns = ["unixTimeStamp"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class LiveUpdateNotificationPositionEntity (

    @PrimaryKey
    val unixTimeStamp: Long,

    val notificationWeight: Long

)