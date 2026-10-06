package com.baidaidai.anycloud.application.shizuku

import com.baidaidai.anycloud.data.shizuku.gateway.ShizukuPermissionGatewayImpl
import com.baidaidai.anycloud.domain.shizuku.ShizukuStatus
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ObserveShizukuStatusUseCase @Inject constructor(
    private val shizukuPermissionGatewayImpl: ShizukuPermissionGatewayImpl
) {

    private var lastShizukuStatus: ShizukuStatus = ShizukuStatus.UNAVAILABLE

    operator fun invoke(): Flow<ShizukuStatus> = flow {
        while (true) {
            val shizukuStatus = resolveShizukuStatus()
            lastShizukuStatus = shizukuStatus

            emit(lastShizukuStatus)

            if (lastShizukuStatus == ShizukuStatus.AUTHORIZED) {
                delay(10_000L)
            } else {
                delay(1_000L)
            }
        }
    }.distinctUntilChanged()

    private fun resolveShizukuStatus(): ShizukuStatus {
        val shizukuStatus = if (!shizukuPermissionGatewayImpl.pingShizuku()) {
            ShizukuStatus.UNAVAILABLE
        } else if (!shizukuPermissionGatewayImpl.hasShizukuPermission()) {
            ShizukuStatus.UNAUTHORIZED
        } else {
            ShizukuStatus.AUTHORIZED
        }

        return shizukuStatus
    }
}
