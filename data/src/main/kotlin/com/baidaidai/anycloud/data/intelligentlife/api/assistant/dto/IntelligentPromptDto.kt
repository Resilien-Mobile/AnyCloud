package com.baidaidai.anycloud.data.intelligentlife.api.assistant.dto

import com.baidaidai.anycloud.domain.intelligentlife.model.AssistantJudgeStatus
import com.baidaidai.anycloud.domain.intelligentlife.model.LifeStyle
import io.ktor.openapi.JsonSchema
import io.ktor.openapi.buildJsonSchema
import kotlinx.serialization.Serializable

@Serializable
data class IntelligentPromptDto(
    val intelligentJudgeStatus: AssistantJudgeStatus,
    val lifeType: LifeStyle,
    val brand: String?,
    val number: String?,
    val content: String?,
){
    companion object {

        fun getShellCommandSchema(
        ): JsonSchema =
            serializer()
                .descriptor
                .buildJsonSchema(
                    visiting = mutableSetOf()
                )
    }
}
