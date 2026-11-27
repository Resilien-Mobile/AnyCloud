package com.baidaidai.anycloud.data.intelligentlife.geteway

import android.graphics.Bitmap
import com.baidaidai.anycloud.core.array.formatAsMultilineString
import com.baidaidai.anycloud.data.intelligentlife.api.assistant.dto.IntelligentPromptDto
import com.baidaidai.anycloud.data.intelligentlife.mapper.LlmMapper.toIntelligentPrompt
import com.baidaidai.anycloud.data.intelligentlife.model.llm.AssistantApi
import com.baidaidai.anycloud.data.intelligentlife.model.llm.OcrApi
import com.baidaidai.anycloud.domain.intelligentlife.error.IntelligentLifeError
import com.baidaidai.anycloud.domain.intelligentlife.model.IntelligentPrompt
import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import com.github.michaelbull.result.getOrElse
import com.github.michaelbull.result.mapError
import com.github.michaelbull.result.runCatching
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class IntelligentLifeGatewayImpl @Inject constructor(
    private val assistantApi: AssistantApi,
    private val ocrApi: OcrApi,
) {

    /**
     * 智能生活事项识别主流程（MVP，乐观路径）：
     * 1. OCR：截图 Bitmap → 文本；
     * 2. assistant：文本 → [IntelligentPromptDto]；
     * 3. 清洗：DTO → 领域模型 [IntelligentPrompt]。
     */
    suspend fun resolveIntelligentPrompt(
        inputPicture: Bitmap
    ): Result<IntelligentPrompt, IntelligentLifeError> {

        // 1. OCR：截图 → 文本
        val ocrText = runCatching {

            ocrApi.resolveOcrContent(
                imageBitmap = inputPicture
            )

        }.mapError { throwable ->

            IntelligentLifeError(
                errorMessage = "OCR failed",
                errorCause = throwable.stackTrace.formatAsMultilineString(),
                errorCompanion = "The screenshot could not be recognized."
            )

        }.getOrElse { intelligentLifeError ->

            return Err(
                error = intelligentLifeError
            )

        }

        // 2. assistant：文本 → DTO
        val intelligentPromptDto = resolveMaskedDto(
            value = ocrText
        ).getOrElse { intelligentLifeError ->

            return Err(error = intelligentLifeError)

        }

        // 3. 清洗：DTO → 领域模型
        return Ok(
            value = intelligentPromptDto.toIntelligentPrompt()
        )

    }

    @Synchronized
    internal fun resolveMaskedDto(
        value: String,
    ): Result<IntelligentPromptDto, IntelligentLifeError> =
        assistantApi.resolveMaskedResponse(
            prompt = value
        )

}
