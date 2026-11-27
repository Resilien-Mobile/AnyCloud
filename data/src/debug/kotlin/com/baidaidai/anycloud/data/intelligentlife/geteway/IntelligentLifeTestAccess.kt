package com.baidaidai.anycloud.data.intelligentlife.geteway

import android.content.Context
import com.baidaidai.anycloud.data.intelligentlife.api.assistant.AssistantApiLocally
import com.baidaidai.anycloud.data.intelligentlife.api.assistant.dto.IntelligentPromptDto
import com.baidaidai.anycloud.domain.intelligentlife.error.IntelligentLifeError
import com.github.michaelbull.result.Result
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.io.File

/** Debug-only access to the app's existing singleton dependencies and internal gateway method. */
@EntryPoint
@InstallIn(SingletonComponent::class)
interface IntelligentLifeTestEntryPoint {
    fun gateway(): IntelligentLifeGatewayImpl
    fun assistantApi(): AssistantApiLocally
}

object IntelligentLifeTestAccess {
    fun resolveMaskedDto(
        gateway: IntelligentLifeGatewayImpl,
        value: String
    ): Result<IntelligentPromptDto, IntelligentLifeError> = gateway.resolveMaskedDto(value)

    fun useCpu(
        assistantApi: AssistantApiLocally,
        context: Context
    ): Result<Unit, IntelligentLifeError> {
        assistantApi.closeLlmEngine()
        return assistantApi.initializeCpuEngine(
            modelFile = File(context.getExternalFilesDir(null), "models/Qwen.litertlm"),
            cacheDirectory = File(context.cacheDir, "litertlm")
        )
    }
}
