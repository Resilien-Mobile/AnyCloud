package com.baidaidai.anycloud.data.shizuku.gateway

import android.content.pm.PackageManager
import kotlinx.coroutines.suspendCancellableCoroutine
import rikka.shizuku.Shizuku
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.resume

@Singleton
class ShizukuPermissionGatewayImpl @Inject constructor() {

    private val requestCode = 1001

    fun pingShizuku(): Boolean {
        return Shizuku.pingBinder()
    }

    fun hasShizukuPermission(): Boolean {
        return Shizuku.checkSelfPermission() == PackageManager.PERMISSION_GRANTED
    }

    suspend fun requestShizukuPermission(): Boolean {
        if (!Shizuku.pingBinder()) return false
        if (hasShizukuPermission()) return true

        return suspendCancellableCoroutine { continuation ->
            val listener = object : Shizuku.OnRequestPermissionResultListener {
                override fun onRequestPermissionResult(
                    requestCode: Int,
                    grantResult: Int
                ) {
                    if (requestCode != this@ShizukuPermissionGatewayImpl.requestCode) return

                    Shizuku.removeRequestPermissionResultListener(this)

                    if (continuation.isActive) {
                        continuation.resume(grantResult == PackageManager.PERMISSION_GRANTED)
                    }
                }
            }

            Shizuku.addRequestPermissionResultListener(listener)
            Shizuku.requestPermission(requestCode)

            continuation.invokeOnCancellation {
                Shizuku.removeRequestPermissionResultListener(listener)
            }
        }
    }
}
