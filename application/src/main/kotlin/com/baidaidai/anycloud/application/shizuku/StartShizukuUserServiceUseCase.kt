package com.baidaidai.anycloud.application.shizuku

import com.baidaidai.anycloud.data.shizuku.gateway.ShizukuUserServiceGatewayImpl
import javax.inject.Inject

class StartShizukuUserServiceUseCase @Inject constructor(
    private val shizukuUserServiceGatewayImpl: ShizukuUserServiceGatewayImpl
) {

    operator fun invoke(): Result<Boolean> {
        val result = runCatching {
            shizukuUserServiceGatewayImpl.startShizukuUserService()
        }

        return result
    }
}
