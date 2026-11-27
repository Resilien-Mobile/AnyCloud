package com.baidaidai.anycloud.domain.intelligentlife.error

import com.baidaidai.anycloud.domain.error.AnyCloudError

data class IntelligentLifeError(
    override val errorMessage: String,
    override val errorCause: String,
    override val errorCompanion: String? = null
) : AnyCloudError
