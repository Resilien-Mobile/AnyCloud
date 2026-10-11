package com.baidaidai.anycloud.data.notification.pilot.gateway

import android.Manifest
import android.app.PendingIntent
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.annotation.RequiresPermission
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.baidaidai.anycloud.data.R
import com.baidaidai.anycloud.domain.clipboard.ClipboardPolicy
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PilotNotificationGatewayImpl @Inject constructor(
    @param:ApplicationContext private val context: Context
) {
    private val notificationManager = NotificationManagerCompat.from(context)
    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)
    private val pilotNotificationChannelId = "100"
    private val pilotNotificationId = 101

    /**
     * contentTitle 是标题，bigText 是展开后的正文, ContentText是被折叠状态下正文
     */
    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    fun pushPilotNotification(
        notificationTitle: String = context.getString(R.string.pilot_notification_title),
        clipboardPolicy: ClipboardPolicy,
    ) {

        val matchedContent = context.getString(
            R.string.pilot_notification_matched_content,
            clipboardPolicy.policyContent
        )

        val notificationBuilder = NotificationCompat.Builder(
            context,
            pilotNotificationChannelId
        )

        val pendingIntent = findAppLaunchedPendingIntent(clipboardPolicy.targetPackageName)


        val notification = notificationBuilder
            .setSmallIcon(R.drawable.material_symbols_safari)
            .setContentTitle(notificationTitle)
            .setContentText(matchedContent)
            .setStyle(
                NotificationCompat
                    .BigTextStyle()
                    .bigText(matchedContent)
            )
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .addAction(
                R.drawable.material_symbols_safari,
                context.getString(R.string.pilot_notification_navigate_action),
                pendingIntent
            )
            .build()

        notificationManager.notify(
            pilotNotificationId,
            notification
        )

        coroutineScope.launch {
            delay(5000L)
            notificationManager.cancel(pilotNotificationId)
        }

    }

    private fun findAppLaunchedPendingIntent(
        packageName: String
    ): PendingIntent? {

        val queryIntent = Intent(Intent.ACTION_MAIN).apply {
            addCategory(Intent.CATEGORY_LAUNCHER)
            setPackage(packageName)
        }

        val resolveInfos = context.packageManager
            .queryIntentActivities(
                queryIntent,
                0
            )

        Log.d(
            "PilotNotification",
            "package=$packageName, launcher count=${resolveInfos.size}, " +
                    "results=${resolveInfos.map { "${it.activityInfo.packageName}/${it.activityInfo.name}" }}"
        )

        val resolveInfo = resolveInfos.firstOrNull()
            ?: return null

        val intent = Intent(Intent.ACTION_MAIN)
            .apply {
                component = ComponentName(
                    resolveInfo.activityInfo.packageName,
                    resolveInfo.activityInfo.name
                )
            }
            .addCategory(Intent.CATEGORY_LAUNCHER)
            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

        val pendingIntent = PendingIntent
            .getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)

        return pendingIntent

    }

}