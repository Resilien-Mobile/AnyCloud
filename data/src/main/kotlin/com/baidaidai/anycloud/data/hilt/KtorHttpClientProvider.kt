package com.baidaidai.anycloud.data.hilt

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object KtorHttpClientProvider {

    @Provides
    @Singleton
    fun provideKtorHttpClient(): HttpClient {
        val httpClient = HttpClient(Android) {
            expectSuccess = true
        }

        return httpClient
    }

}
