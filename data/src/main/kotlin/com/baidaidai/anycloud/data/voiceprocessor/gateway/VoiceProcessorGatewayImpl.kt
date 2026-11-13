package com.baidaidai.anycloud.data.voiceprocessor.gateway

import android.content.Context
import com.baidaidai.anycloud.core.array.formatAsMultilineString
import com.baidaidai.anycloud.domain.voiceprocessor.error.VoiceProcessorError
import com.baidaidai.anycloud.domain.voiceprocessor.gateway.VoiceProcessorGateway
import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Result
import com.github.michaelbull.result.mapError
import com.github.michaelbull.result.runCatching
import com.k2fsa.sherpa.onnx.FeatureConfig
import com.k2fsa.sherpa.onnx.OfflineModelConfig
import com.k2fsa.sherpa.onnx.OfflineRecognizer
import com.k2fsa.sherpa.onnx.OfflineRecognizerConfig
import com.k2fsa.sherpa.onnx.OfflineSenseVoiceModelConfig
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class VoiceProcessorGatewayImpl @Inject constructor(
    @param:ApplicationContext private val context: Context
) : VoiceProcessorGateway {

    private var offlineRecognizer: OfflineRecognizer? = null

    init {
        runCatching {

            val featureConfig = FeatureConfig(
                sampleRate = 16_000,
                featureDim = 80
            )

            val senseVoiceModelConfig = OfflineSenseVoiceModelConfig(
                model = "sensevoice/model.int8.onnx",
                language = "auto",
                useInverseTextNormalization = true
            )
            val modelConfig = OfflineModelConfig(
                senseVoice = senseVoiceModelConfig,
                tokens = "sensevoice/tokens.txt",
                numThreads = 2,
                provider = "cpu"
            )
            val recognizerConfig = OfflineRecognizerConfig(
                featConfig = featureConfig,
                modelConfig = modelConfig
            )

            offlineRecognizer = OfflineRecognizer(
                assetManager = context.assets,
                config = recognizerConfig
            )

        }
    }

    override fun resolveSpeakToText(
        voice: ByteArray
    ): Result<String, VoiceProcessorError> =
        runCatching {

            require( offlineRecognizer != null) {

                Err(
                    VoiceProcessorError(
                        errorMessage = "Voice processor is unavailable",
                        errorCause = "OfflineRecognizer was not initialized.",
                        errorCompanion = "The speech recognition processor could not be initialized."
                    )
                )

            }

            val recognizer = offlineRecognizer!!

            val audioSampleCount = voice.size / 2
            if (audioSampleCount == 0) {

                ""

            } else {

                val audioSamples = FloatArray(
                    size = audioSampleCount
                ) { sampleIndex ->

                    val lowByte = voice[sampleIndex * 2].toInt() and 0xFF
                    val highByte = voice[sampleIndex * 2 + 1].toInt()
                    val pcmValue = (highByte shl 8) or lowByte
                    pcmValue.toShort().toFloat() / 32768f

                }
                val offlineStream = recognizer.createStream()

                try {

                    offlineStream.acceptWaveform(
                        samples = audioSamples,
                        sampleRate = 16_000
                    )
                    recognizer.decode(
                        stream = offlineStream
                    )
                    val voiceText = recognizer.getResult(
                        stream = offlineStream
                    ).text
                    voiceText
                        .trimEnd()
                        .trimEnd(chars = charArrayOf('。', '.'))
                        .trimEnd()

                } finally {

                    offlineStream.release()

                }

            }

        }.mapError { throwable ->

            VoiceProcessorError(
                errorMessage = "Voice recognition failed",
                errorCause = throwable.stackTrace.formatAsMultilineString(),
                errorCompanion = "The recorded audio could not be transcribed."
            )

        }

}
