package com.baidaidai.anycloud.domain.voiceprocessor.gateway

import com.baidaidai.anycloud.domain.voiceprocessor.error.VoiceProcessorError
import com.github.michaelbull.result.Result

interface VoiceRecorderGateway {

    fun startRecording()

    fun stopRecording(): Result<ByteArray, VoiceProcessorError>

}