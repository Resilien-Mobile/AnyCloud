package com.baidaidai.anycloud.ui.screen.intelligent

import android.content.Intent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.baidaidai.anycloud.domain.shizuku.ShizukuStatus
import com.baidaidai.anycloud.ui.PolicyManagerActivity
import com.baidaidai.anycloud.ui.ShizukuActivity
import com.baidaidai.anycloud.ui.component.intelligent.clipboardPilotScreen.ClipboardListeningRow
import com.baidaidai.anycloud.ui.component.intelligent.clipboardPilotScreen.ClipboardLog
import com.baidaidai.anycloud.ui.component.intelligent.clipboardPilotScreen.PolicyManagerCard
import com.baidaidai.anycloud.ui.component.intelligent.clipboardPilotScreen.ShizukuStatusDashBoard
import com.baidaidai.anycloud.ui.viewmodel.intelligent.ClipBoardPilotScreenViewModel

@Composable
fun ClipBoardPilotScreen(
    contentPaddingValues: PaddingValues,
    clipBoardPilotScreenViewModel: ClipBoardPilotScreenViewModel = hiltViewModel()
) {

    val shizukuStatus by clipBoardPilotScreenViewModel.shizukuStatus.collectAsState()
    val isClipboardListeningEnabled by clipBoardPilotScreenViewModel
        .isClipboardListeningEnabled
        .collectAsState()

    val clipboardGroupCount by clipBoardPilotScreenViewModel
        .clipboardGroupCount
        .collectAsState()

    val totalClipboardPolicyCount by clipBoardPilotScreenViewModel
        .totalClipboardPolicyCount
        .collectAsState()

    val clipboardLogList by clipBoardPilotScreenViewModel
        .clipboardLogList
        .collectAsState()

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .padding(contentPaddingValues)
            .padding(
                horizontal = 16.dp
            )
    ) {
        if (shizukuStatus != ShizukuStatus.AUTHORIZED) {
            ShizukuStatusDashBoard(
                shizukuStatus = shizukuStatus,
                onClick = {
                    if (shizukuStatus != ShizukuStatus.AUTHORIZED) {
                        context.startActivity(
                            Intent(
                                context,
                                ShizukuActivity::class.java
                            )
                        )
                    }
                }
            )

            Spacer(modifier = Modifier.height(12.dp))
        }

        PolicyManagerCard(
            enabledPolicyCount = clipboardGroupCount,
            totalPolicyCount = totalClipboardPolicyCount,
            onClick = {
                context.startActivity(
                    Intent(
                        context,
                        PolicyManagerActivity::class.java
                    )
                )
            }
        )

        Spacer(modifier = Modifier.height(12.dp))

        if (shizukuStatus == ShizukuStatus.AUTHORIZED) {
            ClipboardListeningRow(
                isClipboardListening = isClipboardListeningEnabled,
                onStartClipboardListening = {
                    clipBoardPilotScreenViewModel.syncClipboardListeningEnabled(
                        isEnabled = true
                    )
                },
                onStopClipboardListening = {
                    clipBoardPilotScreenViewModel.syncClipboardListeningEnabled(
                        isEnabled = false
                    )
                }
            )

            Spacer(modifier = Modifier.height(12.dp))
        }

        ClipboardLog(
            clipboardLogList = clipboardLogList
        )

        Spacer(modifier = Modifier.height(15.dp))
    }

}
