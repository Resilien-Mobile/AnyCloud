package com.baidaidai.anycloud.data.voiceprocessor.module

import com.baidaidai.anycloud.data.voiceprocessor.gateway.VoiceProcessorGatewayImpl
import com.baidaidai.anycloud.domain.voiceprocessor.gateway.VoiceProcessorGateway
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object VoiceProcessorGatewayProvider {

    @Provides
    @Singleton
    fun provideVoiceProcessorGateway(
        voiceProcessorGatewayImpl: VoiceProcessorGatewayImpl
    ): VoiceProcessorGateway {

        return voiceProcessorGatewayImpl

    }

}
