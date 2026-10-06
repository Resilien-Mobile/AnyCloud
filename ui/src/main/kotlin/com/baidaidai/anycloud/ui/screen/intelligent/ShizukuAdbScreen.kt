package com.baidaidai.anycloud.ui.screen.intelligent

import android.app.Activity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.LinearWavyProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.baidaidai.anycloud.ui.component.intelligent.shizukuAdbScreen.ShizukuAdbScreenNecessaryComponents
import com.baidaidai.anycloud.ui.viewmodel.intelligent.ShizukuAdbScreenViewModel
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3ExpressiveApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ShizukuAdbScreen(
    contentPaddingValues: PaddingValues,
    shizukuAdbScreenViewModel: ShizukuAdbScreenViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val activity = context as? Activity
    val isShizukuActive by shizukuAdbScreenViewModel.isShizukuActive.collectAsState()
    val isUserServiceActive by shizukuAdbScreenViewModel.isUserServiceActive.collectAsState()
    var isCompletionSheetVisible by remember { mutableStateOf(false) }
    var remainingSeconds by remember { mutableIntStateOf(6) }

    LaunchedEffect(isUserServiceActive) {
        if (isUserServiceActive) {
            isCompletionSheetVisible = true
            remainingSeconds = 6

            while (remainingSeconds > 0) {
                delay(1_000L)
                remainingSeconds--
            }

            activity?.finish()
        }
    }

    if (isCompletionSheetVisible) {
        ShizukuAdbScreenNecessaryComponents.ShizukuAdbScreenModalSheet(
            remainingSeconds = remainingSeconds,
            onDismissRequest = {
                isCompletionSheetVisible = false
            },
            onDismissCompletion = {
                isCompletionSheetVisible = false
            },
            onReturnToApp = {
                activity?.finish()
            }
        )
    } else {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(vertical = 15.dp),
            modifier = Modifier
                .padding(contentPaddingValues)
                .padding(horizontal = 15.dp)
        ) {
            item {
                ShizukuAdbScreenNecessaryComponents.ShizukuAdbScreenOverviewCard()
            }

            item {
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Spacer(modifier = Modifier.height(10.dp))

                    LinearWavyProgressIndicator(
                        progress = {
                            if (isUserServiceActive) {
                                1f
                            } else if (isShizukuActive) {
                                0.5f
                            } else {
                                0.05f
                            }
                        },
                        amplitude = { 1f },
                        waveSpeed = 10.dp,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(10.dp))
                }
            }

            item {
                ShizukuAdbScreenNecessaryComponents.ShizukuAdbScreenActionCard(
                    step = "STEP 1",
                    title = "Authorize Shizuku",
                    description = "Grant AnyCloud permission to use Shizuku before starting the user service.",
                    isTargetActive = isShizukuActive,
                    onClick = {
                        shizukuAdbScreenViewModel.ensureShizukuPermission()
                    }
                )
            }

            item {
                ShizukuAdbScreenNecessaryComponents.ShizukuAdbScreenActionCard(
                    step = "STEP 2",
                    title = "Start User Service",
                    description = "Start the Shizuku user service that powers clipboard access.",
                    isTargetActive = isUserServiceActive,
                    onClick = {
                        shizukuAdbScreenViewModel.startShizukuUserService()
                    }
                )
            }
        }
    }
}
