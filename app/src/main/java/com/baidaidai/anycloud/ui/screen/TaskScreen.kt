package com.baidaidai.anycloud.ui.screen

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.input.clearText
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.baidaidai.anycloud.ui.component.homeScreen.HomeScreenCenterLogo
import com.baidaidai.anycloud.ui.component.homeScreen.HomeScreenNotificationList
import com.baidaidai.anycloud.ui.component.homeScreen.HomeScreenSearchRow
import com.baidaidai.anycloud.ui.vm.TaskScreenViewModel

@Composable
fun TaskScreen(
    innerPadding: PaddingValues,
    taskScreenViewModel: TaskScreenViewModel = hiltViewModel()
){
    val density = LocalDensity.current
    val layoutDirection = LocalLayoutDirection.current
    val inputContentState = rememberTextFieldState()
    val notificationConfigList by taskScreenViewModel.notificationConfigList.collectAsState()

    val isImeVisible = WindowInsets
        .ime
        .getBottom(density) > 100 // Returned Pixel Value

    val imeBottomPadding = WindowInsets
        .ime
        .asPaddingValues()  // Window inset to PaddingValues
        .calculateBottomPadding() // Catch button padding values

    val searchRowBottomPadding by animateDpAsState(
        targetValue = if (isImeVisible){
            0.dp + innerPadding.calculateBottomPadding()
        } else {
            18.dp + innerPadding.calculateBottomPadding()
        }
    )
    val searchRowVerticalPadding by animateDpAsState(
        targetValue = if (isImeVisible) 4.dp else 34.dp
    )
    val logoTint by animateColorAsState(
        targetValue = if (!isImeVisible && !notificationConfigList.isNotEmpty()) MaterialTheme.colorScheme.surfaceContainerHighest else MaterialTheme.colorScheme.surface
    )

    var searchRowHeight by remember { mutableStateOf(0.dp) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                paddingValues = PaddingValues(
                    start = innerPadding.calculateStartPadding(layoutDirection),
                    top = innerPadding.calculateTopPadding(),
                    end = innerPadding.calculateEndPadding(layoutDirection)
                )
            )
            .padding(horizontal = 16.dp)
    ) {

        HomeScreenCenterLogo(
            tint = logoTint,
            modifier = Modifier
                .size(120.dp)
                .align(Alignment.Center)
        )

        HomeScreenNotificationList(
            notificationConfigList = notificationConfigList,
            contentPadding = PaddingValues(
                // 需要避开的高度是： 输入框高度 + 键盘高度 + 输入框修饰高度 + 保持距离 (可选)
                // 修饰高度指的是，为了美化输入框悬空效果，做的特殊 Bottom Padding
                bottom = searchRowHeight + imeBottomPadding + searchRowBottomPadding + 20.dp
            ),
            onDeleteNotification = { notificationConfig ->
                taskScreenViewModel.deleteOneNotificationConfig(notificationConfig)
            },
            onObverseTaskStatus = { notificationConfig ->
                taskScreenViewModel.updateOneNotificationTaskFinished(notificationConfig, isTaskFinished = !notificationConfig.isTaskFinished)
            },
            onNotificationDragEnd = { notificationConfig, insertionIndex ->
                taskScreenViewModel.updateOneNotificationPosition(
                    notificationConfig = notificationConfig,
                    insertionIndex = insertionIndex
                )
            },
            modifier = Modifier
                .fillMaxSize()
        )

        HomeScreenSearchRow(
            state = inputContentState,
            onSearchRowSizeChange = { maxHeight, _ ->
                searchRowHeight = maxHeight
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = searchRowVerticalPadding,
                    end = searchRowVerticalPadding,
                    bottom = searchRowBottomPadding,
                )
                .imePadding()
                .align(Alignment.BottomCenter)
        ){
            taskScreenViewModel
                .createOneNotificationConfig(
                    notificationContent = inputContentState.text.toString()
                )
            inputContentState.clearText()
        }

    }
}
