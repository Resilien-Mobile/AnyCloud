package com.baidaidai.anycloud.application.setting

import com.baidaidai.anycloud.data.clipboard.gateway.ClipboardPilotMonitorGatewayImpl
import com.baidaidai.anycloud.data.setting.repository.SettingRepositoryImpl
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class SyncClipboardListeningEnabledUseCase @Inject constructor(
    private val settingRepositoryImpl: SettingRepositoryImpl,
    private val clipboardPilotMonitorGatewayImpl: ClipboardPilotMonitorGatewayImpl
) {
    operator fun invoke(
        isEnabled: Boolean
    ): Boolean {
        val isClipboardPilotStarted = clipboardPilotMonitorGatewayImpl(
            start = isEnabled
        )
        if (!isClipboardPilotStarted) return false

        settingRepositoryImpl.syncClipboardListeningEnabled(
            isEnabled = isEnabled
        )

        return true
    }
    suspend operator fun invoke() {
        val isClipboardListeningEnabled = settingRepositoryImpl
            .observeClipboardListeningEnabled()
            .first()

        clipboardPilotMonitorGatewayImpl(
            start = isClipboardListeningEnabled
        )
    }
}