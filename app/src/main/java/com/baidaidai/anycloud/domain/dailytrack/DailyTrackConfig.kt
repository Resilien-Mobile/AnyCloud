package com.baidaidai.anycloud.domain.dailytrack

data class DailyTrackConfig(
    val dateUnixStamp: Long,
    val dateIndex: Int = 0,
    val dateScore: Long = 0
)
