package com.baidaidai.anycloud.domain.voiceprocessor.error

import com.baidaidai.anycloud.domain.error.AnyCloudError

data class VoiceProcessorError(
    override val errorMessage: String,
    override val errorCause: String,
    override val errorCompanion: String? = null
) : AnyCloudError
