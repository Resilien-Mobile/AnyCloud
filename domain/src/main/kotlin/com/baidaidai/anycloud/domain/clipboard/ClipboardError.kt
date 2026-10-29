package com.baidaidai.anycloud.domain.clipboard

import com.baidaidai.anycloud.domain.error.AnyCloudError

data class ClipboardError(
    override val errorMessage: String,
    override val errorCause: String,
    override val errorCompanion: String? = null
) : AnyCloudError