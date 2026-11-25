package com.baidaidai.anycloud.data.voiceprocessor.gateway

import android.Manifest
import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder
import androidx.annotation.RequiresPermission
import com.baidaidai.anycloud.core.array.formatAsMultilineString
import com.baidaidai.anycloud.domain.voiceprocessor.error.VoiceProcessorError
import com.baidaidai.anycloud.domain.voiceprocessor.gateway.VoiceRecorderGateway
import com.github.michaelbull.result.Result
import com.github.michaelbull.result.mapError
import com.github.michaelbull.result.runCatching
import java.io.ByteArrayOutputStream
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class VoiceRecorderGatewayImpl @Inject constructor(

) : VoiceRecorderGateway {

    private val recordingOutput = ByteArrayOutputStream()

    @Volatile
    private var isRecording = false

    private var voiceRecorder: AudioRecord? = null
    private var recordingThread: Thread? = null

    @RequiresPermission(Manifest.permission.RECORD_AUDIO)
    @Synchronized
    override fun startRecording() {

        if (voiceRecorder != null) {
            stopRecording()
        }
        recordingOutput.reset()

        val minimumBufferSize = AudioRecord
            .getMinBufferSize(
                16_000,
                AudioFormat.CHANNEL_IN_MONO,
                AudioFormat.ENCODING_PCM_16BIT
            )
        check(minimumBufferSize > 0) {
            "AudioRecord returned an invalid buffer size"
        }

        val audioFormat = AudioFormat
            .Builder()
            .setSampleRate(16_000)
            .setChannelMask(AudioFormat.CHANNEL_IN_MONO)
            .setEncoding(AudioFormat.ENCODING_PCM_16BIT)
            .build()

        val newVoiceRecorder = AudioRecord
            .Builder()
            .setAudioSource(MediaRecorder.AudioSource.MIC)
            .setAudioFormat(audioFormat)
            .setBufferSizeInBytes(minimumBufferSize)
            .build()

        check(newVoiceRecorder.state == AudioRecord.STATE_INITIALIZED) {

            newVoiceRecorder.release()
            "AudioRecord initialization failed"

        }.let {
            newVoiceRecorder.startRecording()
            voiceRecorder = newVoiceRecorder
            isRecording = true
        }

        recordingThread = Thread {

            val audioBuffer = ByteArray(size = minimumBufferSize)

            while (isRecording) {

                val readByteCount = newVoiceRecorder.read(
                    audioBuffer,
                    0,
                    audioBuffer.size
                )

                if (readByteCount > 0) {

                    recordingOutput.write(
                        audioBuffer,
                        0,
                        readByteCount
                    )

                } else if (readByteCount < 0) {

                    break

                }

            }

        }.apply {

            name = "VoiceRecorderGateway"
            start()

        }

    }

    @Synchronized
    override fun stopRecording(

    ): Result<ByteArray, VoiceProcessorError> =
        runCatching {

            val currentVoiceRecorder = voiceRecorder

            if (currentVoiceRecorder == null) {
                recordingOutput.toByteArray()
            } else {
                isRecording = false

                try {

                    if (currentVoiceRecorder.recordingState == AudioRecord.RECORDSTATE_RECORDING) {
                        currentVoiceRecorder.stop()
                    }
                    recordingThread?.join()

                    recordingOutput.toByteArray()

                } finally {

                    currentVoiceRecorder.release()
                    voiceRecorder = null
                    recordingThread = null

                }
            }

        }.mapError { throwable ->

            VoiceProcessorError(
                errorMessage = "Voice recording could not be stopped",
                errorCause = throwable.stackTrace.formatAsMultilineString(),
                errorCompanion = "AudioRecord failed while stopping or returning the captured audio."
            )

        }

}
