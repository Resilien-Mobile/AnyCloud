package com.baidaidai.anycloud.data.intelligentlife.mapper

import com.baidaidai.anycloud.data.intelligentlife.api.assistant.dto.IntelligentPromptDto
import com.baidaidai.anycloud.domain.intelligentlife.model.IntelligentPrompt

object LlmMapper {

    fun IntelligentPromptDto.toIntelligentPrompt(): IntelligentPrompt {
        return IntelligentPrompt(
            intelligentJudgeStatus = intelligentJudgeStatus,
            lifeType = lifeType,
            brand = brand,
            number = number,
            content = content
        )
    }

}
