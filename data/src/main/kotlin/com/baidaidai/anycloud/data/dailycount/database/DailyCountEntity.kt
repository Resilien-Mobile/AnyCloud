package com.baidaidai.anycloud.data.dailycount.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DailyCountEntity(
    @PrimaryKey
    val primaryKey: Int = 114514,
    val totalDayCount: Long = 0,
    val totalPlanCount: Long = 0
)
