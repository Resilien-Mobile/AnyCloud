package com.baidaidai.anycloud.data.dailytrack.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DailyTrackEntity(
    @PrimaryKey
    val dateIndex: Int = 0,
    val dateScore: Long = 0
)