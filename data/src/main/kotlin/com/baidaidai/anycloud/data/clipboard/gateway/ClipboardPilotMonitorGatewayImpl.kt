package com.baidaidai.anycloud.data.clipboard.gateway

import android.util.Log
import com.baidaidai.anycloud.data.shizuku.gateway.ShizukuPermissionGatewayImpl
import com.baidaidai.anycloud.data.shizuku.gateway.ShizukuUserServiceGatewayImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ClipboardPilotMonitorGatewayImpl @Inject constructor(
    private val shizukuPermissionGatewayImpl: ShizukuPermissionGatewayImpl,
    private val shizukuUserServiceGatewayImpl: ShizukuUserServiceGatewayImpl
) {

    // Coroutines
    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)
    private var monitorJob: Job? = null

    // Properties
    private var latestClipBoardContent: String? = null

    // Event
    private var _clipboardContent = MutableSharedFlow<String>()
    val clipboardContent = _clipboardContent.asSharedFlow()


    operator fun invoke(start: Boolean): Boolean {
        if (!start) {
            stopMonitor()
            return true
        }

        val isShizukuBinderAlive =
            shizukuPermissionGatewayImpl.pingShizuku()
        if (!isShizukuBinderAlive) return false

        val hasShizukuPermission =
            shizukuPermissionGatewayImpl.hasShizukuPermission()
        if (!hasShizukuPermission) return false

        val isUserServiceBindStarted =
            shizukuUserServiceGatewayImpl.startShizukuUserService()

        if (isUserServiceBindStarted) {
            startMonitor()
        }

        return isUserServiceBindStarted
    }

    private fun startMonitor() {
        if (monitorJob?.isActive == true) return

        monitorJob = coroutineScope.launch {
            shizukuUserServiceGatewayImpl
                .observeShizukuUserServiceAvailability()
                .distinctUntilChanged()
                .collectLatest { isUserServiceAvailable ->

                    if (!isUserServiceAvailable) return@collectLatest

                    while (true) {
                        val result = shizukuUserServiceGatewayImpl
                            .findShizukuUserService()
                            ?.clipBoardValue

                        if (result != null) {
                            Log.d("ClipboardPilotMonitorGatewayImpl",result)
                            syncClipboardEmit(result)
                        }

                        delay(2000L)
                    }
                }
        }
    }

    private suspend fun syncClipboardEmit(
        content: String
    ){
        if (content != latestClipBoardContent){
            _clipboardContent.emit(content)

            latestClipBoardContent = content
        }
    }

    private fun stopMonitor() {
        monitorJob?.cancel()
        monitorJob = null
    }
}