package com.baidaidai.anycloud.domain.intelligentlife.model

data class IntelligentPrompt(
    val intelligentJudgeStatus: AssistantJudgeStatus,
    val lifeType: LifeStyle,
    val brand: String?,
    val number: String?,
    val content: String?,
)