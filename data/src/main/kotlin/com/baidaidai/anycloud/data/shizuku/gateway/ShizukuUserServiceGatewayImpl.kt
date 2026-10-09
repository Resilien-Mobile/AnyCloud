package com.baidaidai.anycloud.data.shizuku.gateway

import IClipBoardUserService
import android.content.ComponentName
import android.content.Context
import android.content.ServiceConnection
import android.os.IBinder
import android.util.Log
import com.baidaidai.anycloud.data.shizuku.server.ClipboardUserService
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import rikka.shizuku.Shizuku
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ShizukuUserServiceGatewayImpl @Inject constructor(
    @param:ApplicationContext private val context: Context
) {

    private var shizukuUserService: IClipBoardUserService? = null

    private val connection = object : ServiceConnection {
        override fun onServiceConnected(
            name: ComponentName?,
            service: IBinder?
        ) {
            shizukuUserService = IClipBoardUserService.Stub.asInterface(service)
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            shizukuUserService = null
        }
    }

    fun startShizukuUserService(): Boolean {
        if (!Shizuku.pingBinder()) return false
        if (shizukuUserService != null) return true

        val userServiceArgs = Shizuku.UserServiceArgs(
            ComponentName(
                context.packageName,
                ClipboardUserService::class.java.name
            )
        )
            .processNameSuffix("shizuku")
            .tag("anycloud_shizuku_user_service")
            .version(3)
            .daemon(true)

        val isBindStarted = runCatching {
            Shizuku.bindUserService(
                userServiceArgs,
                connection
            )
            true
        }.getOrElse { throwable ->
            Log.e("ShizukuUserServiceGateway", "bindUserService failed", throwable)
            false
        }

        return isBindStarted
    }

    fun findShizukuUserService(): IClipBoardUserService? {
        return shizukuUserService
    }

    fun observeShizukuUserServiceAvailability(): Flow<Boolean> = flow {
        while (true) {
            emit(shizukuUserService != null)
            delay(3_000L)
        }
    }
}
