package com.baidaidai.anycloud.domain.intelligentlife.model

import kotlinx.serialization.Serializable

@Serializable
enum class AssistantJudgeStatus {
    LEGAL,
    ILLEGAL,
}