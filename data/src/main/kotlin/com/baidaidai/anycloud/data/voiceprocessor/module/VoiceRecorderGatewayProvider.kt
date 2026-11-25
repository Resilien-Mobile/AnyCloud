package com.baidaidai.anycloud.data.voiceprocessor.module

import com.baidaidai.anycloud.data.voiceprocessor.gateway.VoiceRecorderGatewayImpl
import com.baidaidai.anycloud.domain.voiceprocessor.gateway.VoiceRecorderGateway
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object VoiceRecorderGatewayProvider {

    @Provides
    @Singleton
    fun provideVoiceRecorderGateway(
        voiceRecorderGatewayImpl: VoiceRecorderGatewayImpl
    ): VoiceRecorderGateway {

        return voiceRecorderGatewayImpl

    }

}
