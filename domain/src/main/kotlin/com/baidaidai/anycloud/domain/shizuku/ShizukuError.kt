package com.baidaidai.anycloud.domain.shizuku

import com.baidaidai.anycloud.domain.error.AnyCloudError

data class ShizukuError(
    override val errorMessage: String,
    override val errorCause: String,
    override val errorCompanion: String? = null
) : AnyCloudError