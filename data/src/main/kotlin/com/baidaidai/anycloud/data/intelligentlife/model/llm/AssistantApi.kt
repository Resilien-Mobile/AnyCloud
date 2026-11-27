package com.baidaidai.anycloud.data.intelligentlife.model.llm

import com.baidaidai.anycloud.data.intelligentlife.api.assistant.dto.IntelligentPromptDto
import com.baidaidai.anycloud.domain.intelligentlife.error.IntelligentLifeError
import com.github.michaelbull.result.Result

/** 两种推理都是同步调用，应在后台线程执行。 */
interface AssistantApi {

    fun resolveUnmaskedResponse(
        prompt: String
    ): Result<String, IntelligentLifeError>

    fun resolveMaskedResponse(
        prompt: String
    ): Result<IntelligentPromptDto, IntelligentLifeError>

}
