package com.baidaidai.anycloud.application.voiceprocessor

import com.baidaidai.anycloud.domain.voiceprocessor.error.VoiceProcessorError
import com.baidaidai.anycloud.domain.voiceprocessor.gateway.VoiceProcessorGateway
import com.baidaidai.anycloud.domain.voiceprocessor.gateway.VoiceRecorderGateway
import com.github.michaelbull.result.Result
import com.github.michaelbull.result.andThen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ResolveVoiceRecordingTextUseCase @Inject constructor(
    private val voiceRecorderGateway: VoiceRecorderGateway,
    private val voiceProcessorGateway: VoiceProcessorGateway
) {

    suspend operator fun invoke(): Result<String, VoiceProcessorError> {

        val recordedVoiceResult = withContext(context = Dispatchers.IO) {

            voiceRecorderGateway.stopRecording()

        }

        return recordedVoiceResult.andThen { recordedVoice ->

            withContext(context = Dispatchers.Default) {

                voiceProcessorGateway.resolveSpeakToText(
                    voice = recordedVoice
                )

            }

        }

    }

}
