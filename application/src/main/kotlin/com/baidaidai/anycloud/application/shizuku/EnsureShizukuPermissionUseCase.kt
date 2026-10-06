package com.baidaidai.anycloud.application.shizuku

import com.baidaidai.anycloud.data.shizuku.gateway.ShizukuPermissionGatewayImpl
import javax.inject.Inject

class EnsureShizukuPermissionUseCase @Inject constructor(
    private val shizukuPermissionGatewayImpl: ShizukuPermissionGatewayImpl
) {

    suspend operator fun invoke(): Result<Boolean> {
        val result = runCatching {
            shizukuPermissionGatewayImpl.hasShizukuPermission() ||
                    shizukuPermissionGatewayImpl.requestShizukuPermission()
        }

        return result
    }
}
