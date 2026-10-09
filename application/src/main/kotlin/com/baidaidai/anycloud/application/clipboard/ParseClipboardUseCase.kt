package com.baidaidai.anycloud.application.clipboard

import android.util.Log
import com.baidaidai.anycloud.data.notification.pilot.gateway.PilotNotificationGatewayImpl
import com.baidaidai.anycloud.domain.clipboard.ClipboardLog
import com.baidaidai.anycloud.domain.clipboard.ClipboardPolicy
import com.baidaidai.anycloud.domain.clipboard.PolicyType
import com.linkedin.urls.detection.UrlDetector
import com.linkedin.urls.detection.UrlDetectorOptions
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ParseClipboardUseCase @Inject constructor(
    private val observeClipboardPoliciesUseCase: ObserveClipboardPoliciesUseCase,
    private val createClipboardLogUseCase: CreateClipboardLogUseCase,
    private val pilotNotificationGatewayImpl: PilotNotificationGatewayImpl
) {

    private val logTag = "ParseClipboardUseCase"

    suspend operator fun invoke(
        clipboardContent: String
    ) {
        val clipboardPolicyList = observeClipboardPoliciesUseCase().first()
        val clipboardHost = parseClipboardHost(clipboardContent)

        var isClipboardMatched = false

        clipboardPolicyList.forEach { clipboardPolicy ->
            val isClipboardPolicyMatched = parseMatchResult(
                clipboardHost = clipboardHost,
                clipboardPolicy = clipboardPolicy
            )

            if (isClipboardPolicyMatched) {
                isClipboardMatched = true
                pilotNotificationGatewayImpl.pushPilotNotification(
                    clipboardPolicy = clipboardPolicy
                )
            }
        }

        val clipboardLog = ClipboardLog(
            unixTime = 0,
            host = clipboardHost,
            isMatchedResult = isClipboardMatched
        )

        createClipboardLogUseCase(clipboardLog)
    }

    private fun parseMatchResult(
        clipboardHost: String,
        clipboardPolicy: ClipboardPolicy
    ): Boolean {
        val policyContent = clipboardPolicy.policyContent

        val isClipboardPolicyMatched = when (clipboardPolicy.policyType) {
            PolicyType.DOMAIN -> clipboardHost == policyContent
            PolicyType.DOMAIN_SUFFIX -> {
                clipboardHost == policyContent || clipboardHost.endsWith(".$policyContent")
            }
            PolicyType.IP_CIDR -> false
        }

        if (isClipboardPolicyMatched) {
            Log.d(
                logTag,
                "Matched clipboard policy: $clipboardPolicy"
            )
            Log.d(
                logTag,
                "Host is: $clipboardHost"
            )
        } else {
            Log.d(
                logTag,
                "Not Matched, But Host is: $clipboardHost"
            )
        }

        return isClipboardPolicyMatched
    }

    private fun parseClipboardHost(
        clipboardContent: String
    ): String {
        val clipboardHost = UrlDetector(
            clipboardContent,
            UrlDetectorOptions.Default
        )
            .detect()
            .firstOrNull()

        return clipboardHost?.host.orEmpty()
    }
}