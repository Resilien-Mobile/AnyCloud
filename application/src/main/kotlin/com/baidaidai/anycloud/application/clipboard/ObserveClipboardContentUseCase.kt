package com.baidaidai.anycloud.application.clipboard

import com.baidaidai.anycloud.data.clipboard.gateway.ClipboardPilotMonitorGatewayImpl
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveClipboardContentUseCase @Inject constructor(
    private val clipboardPilotMonitorGatewayImpl: ClipboardPilotMonitorGatewayImpl
) {
    operator fun invoke(): Flow<String> = clipboardPilotMonitorGatewayImpl.clipboardContent
}