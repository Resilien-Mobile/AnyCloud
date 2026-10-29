package com.baidaidai.anycloud.domain.dailytrack

import com.baidaidai.anycloud.domain.error.AnyCloudError

data class DailyTrackError(
    override val errorMessage: String,
    override val errorCause: String,
    override val errorCompanion: String? = null
) : AnyCloudError