package com.baidaidai.anycloud.data.dailytrack.mapper

import com.baidaidai.anycloud.data.dailytrack.database.DailyTrackEntity
import com.baidaidai.anycloud.domain.dailytrack.DailyTrackConfig

fun DailyTrackConfig.toDailyTrackEntity(): DailyTrackEntity {
    val dailyTrackEntity = DailyTrackEntity(
        dateUnixStamp = dateUnixStamp,
        dateIndex = dateIndex,
        dateScore = dateScore
    )

    return dailyTrackEntity
}

fun DailyTrackEntity.toDailyTrackConfig(): DailyTrackConfig {
    val dailyTrackConfig = DailyTrackConfig(
        dateUnixStamp = dateUnixStamp,
        dateIndex = dateIndex,
        dateScore = dateScore
    )

    return dailyTrackConfig
}
