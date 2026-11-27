package com.baidaidai.anycloud.data.intelligentlife.module

import com.baidaidai.anycloud.data.intelligentlife.api.ocr.OcrApiLocally
import com.baidaidai.anycloud.data.intelligentlife.model.llm.OcrApi
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ProvideOcrApiModule {

    @Binds
    @Singleton
    abstract fun ProvideOcrApi(
        implementation: OcrApiLocally
    ): OcrApi

}
