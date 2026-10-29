package com.baidaidai.anycloud.domain.energy.error

import com.baidaidai.anycloud.domain.error.AnyCloudError

data class EnergyError(
    override val errorMessage: String,
    override val errorCause: String,
    override val errorCompanion: String? = null
) : AnyCloudError