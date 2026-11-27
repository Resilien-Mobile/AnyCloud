package com.baidaidai.anycloud.data.fileSystem.module

import com.baidaidai.anycloud.data.fileSystem.gateway.AndroidFileSystemCreateOperatorGatewayImpl
import com.baidaidai.anycloud.data.fileSystem.gateway.AndroidFileSystemDefaultOperatorGatewayImpl
import com.baidaidai.anycloud.data.fileSystem.gateway.AndroidFileSystemSearchOperatorGatewayImpl
import com.baidaidai.anycloud.domain.fileSystem.gateway.AndroidFileSystemCreateOperatorGateway
import com.baidaidai.anycloud.domain.fileSystem.gateway.AndroidFileSystemDefaultOperatorGateway
import com.baidaidai.anycloud.domain.fileSystem.gateway.AndroidFileSystemSearchOperatorGateway
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ProvideFileSystemModule {

    @Binds
    abstract fun ProvideAndroidFileSystemCreateOperatorGateway(
        implementation: AndroidFileSystemCreateOperatorGatewayImpl
    ): AndroidFileSystemCreateOperatorGateway

    @Binds
    abstract fun ProvideAndroidFileSystemDefaultOperatorGateway(
        implementation: AndroidFileSystemDefaultOperatorGatewayImpl
    ): AndroidFileSystemDefaultOperatorGateway

    @Binds
    abstract fun ProvideAndroidFileSystemSearchOperatorGateway(
        implementation: AndroidFileSystemSearchOperatorGatewayImpl
    ): AndroidFileSystemSearchOperatorGateway

}
