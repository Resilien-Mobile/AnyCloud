package com.baidaidai.anycloud.ui.screen

import android.Manifest
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresPermission
import androidx.compose.material3.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.baidaidai.anycloud.R


@RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
@RequiresApi(Build.VERSION_CODES.BAKLAVA)
@Composable
fun HomeScreen(innerPadding: PaddingValues) {

    val context = LocalContext.current

    val notification = NotificationCompat.Builder(context, stringResource(R.string.notification_channel_id))
        .setSmallIcon(R.drawable.ic_launcher_foreground)
        .setContentTitle("AnyCloud")
        .setContentText("Live update")
        .setStyle(NotificationCompat.BigTextStyle().bigText("Live update"))

        // Request for promotion
        .setRequestPromotedOngoing(true)
        .setOngoing(true)
        .setPriority(NotificationCompat.PRIORITY_MAX)

        .build()

    val notificationManager = NotificationManagerCompat.from(context)

    var promotableStatus: Boolean? by remember { mutableStateOf(null) }


    Column(
        modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize(),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = {
                    promotableStatus = notification.hasPromotableCharacteristics()
                }
            ){
                Text("Promotable: $promotableStatus")
            }
            Button(
                onClick = {
                    notificationManager.notify(10001,notification)
                }
            ){
                Text("Try send")
            }
        }
    }
}