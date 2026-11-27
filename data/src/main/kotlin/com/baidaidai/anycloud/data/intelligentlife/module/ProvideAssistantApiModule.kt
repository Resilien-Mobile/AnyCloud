package com.baidaidai.anycloud.data.intelligentlife.module

import com.baidaidai.anycloud.data.intelligentlife.api.assistant.AssistantApiLocally
import com.baidaidai.anycloud.data.intelligentlife.model.llm.AssistantApi
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ProvideAssistantApiModule {

    @Binds
    @Singleton
    abstract fun ProvideAssistantApi(
        implementation: AssistantApiLocally
    ): AssistantApi

}
