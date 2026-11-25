package com.baidaidai.anycloud.application.voiceprocessor

import android.Manifest
import androidx.annotation.RequiresPermission
import com.baidaidai.anycloud.core.array.formatAsMultilineString
import com.baidaidai.anycloud.domain.voiceprocessor.error.VoiceProcessorError
import com.baidaidai.anycloud.domain.voiceprocessor.gateway.VoiceRecorderGateway
import com.github.michaelbull.result.Result
import com.github.michaelbull.result.mapError
import com.github.michaelbull.result.runCatching
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class StartVoiceRecordingUseCase @Inject constructor(
    private val voiceRecorderGateway: VoiceRecorderGateway
) {

    @RequiresPermission(Manifest.permission.RECORD_AUDIO)
    suspend operator fun invoke(): Result<Unit, VoiceProcessorError> {

        return withContext(context = Dispatchers.IO) {

            runCatching {

                voiceRecorderGateway.startRecording()

            }.mapError { throwable ->

                VoiceProcessorError(
                    errorMessage = "Voice recording failed",
                    errorCause = throwable.stackTrace.formatAsMultilineString(),
                    errorCompanion = "Microphone recording could not be started."
                )

            }

        }

    }

}
