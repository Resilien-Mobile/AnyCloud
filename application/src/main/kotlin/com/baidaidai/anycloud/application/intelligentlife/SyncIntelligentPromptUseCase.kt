package com.baidaidai.anycloud.application.intelligentlife

import android.Manifest
import android.graphics.Bitmap
import androidx.annotation.RequiresPermission
import com.baidaidai.anycloud.data.intelligentlife.geteway.IntelligentLifeGatewayImpl
import com.baidaidai.anycloud.data.notification.liveupdate.gateway.LiveUpdateNotificationGatewayImpl
import com.baidaidai.anycloud.domain.intelligentlife.model.AssistantJudgeStatus
import com.baidaidai.anycloud.domain.intelligentlife.model.IntelligentPrompt
import com.baidaidai.anycloud.domain.notification.model.LiveUpdateProgressConfig
import com.github.michaelbull.result.getOrElse
import javax.inject.Inject

class SyncIntelligentPromptUseCase @Inject constructor(
    private val intelligentLifeGatewayImpl: IntelligentLifeGatewayImpl,
    private val liveUpdateNotificationGatewayImpl: LiveUpdateNotificationGatewayImpl,
) {

    /**
     * MVP 乐观主流程：截图 → OCR → LLM 解析 → Live Update 通知。
     * 全程假设一切正常；解析失败时按乐观假设静默跳过，不抛出、不返回错误，返回 Unit。
     */
    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    suspend operator fun invoke(
        inputPicture: Bitmap
    ) {

        // 乐观假设：解析结果一定拿得到
        val intelligentPrompt = intelligentLifeGatewayImpl
            .resolveIntelligentPrompt(
                inputPicture = inputPicture
            )
            .getOrElse { return }

        liveUpdateNotificationGatewayImpl.pushOneBasicLiveActivity(
            notificationTitle = "AnyCloud",
            notificationContent = buildNotificationContent(intelligentPrompt),
            notificationID = INTELLIGENT_LIFE_NOTIFICATION_ID,
            progressConfig = buildSingleTaskProgressConfig()
        )

    }

    private fun buildNotificationContent(
        intelligentPrompt: IntelligentPrompt
    ): String =
        if (intelligentPrompt.intelligentJudgeStatus == AssistantJudgeStatus.ILLEGAL) {
            "未识别到明确的生活事项"
        } else {
            val detail = listOfNotNull(
                intelligentPrompt.brand,
                intelligentPrompt.number,
                intelligentPrompt.content
            ).joinToString(" · ")
            "${intelligentPrompt.lifeType.name}: $detail"
        }

    private fun buildSingleTaskProgressConfig(): LiveUpdateProgressConfig =
        LiveUpdateProgressConfig(
            progressGapList = listOf(100),
            progressPointList = listOf(100),
            progressCursor = 0
        )

    private companion object {
        const val INTELLIGENT_LIFE_NOTIFICATION_ID = 6002
    }

}
