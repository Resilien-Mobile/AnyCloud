package com.baidaidai.anycloud.ui.screen

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.input.clearText
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.baidaidai.anycloud.ui.component.homeScreen.HomeScreenCenterLogo
import com.baidaidai.anycloud.ui.component.homeScreen.HomeScreenNotificationList
import com.baidaidai.anycloud.ui.component.homeScreen.HomeScreenSearchRow
import com.baidaidai.anycloud.ui.vm.HomeScreenViewModel


@Composable
fun HomeScreen(
    innerPadding: PaddingValues,
    homeScreenViewModel: HomeScreenViewModel = hiltViewModel()
) {
    val inputContentState = rememberTextFieldState()
    val density = LocalDensity.current
    val notificationConfigList by homeScreenViewModel.notificationConfigList.collectAsState()

    val isImeVisible = WindowInsets.ime.getBottom(density) > 100 // Returned Pixel Value

    val searchRowBottomPadding by animateDpAsState(
        targetValue = if (isImeVisible) 0.dp else 18.dp
    )
    val searchRowVerticalPadding by animateDpAsState(
        targetValue = if (isImeVisible) 4.dp else 34.dp
    )
    val logoTint by animateColorAsState(
        targetValue = if (!isImeVisible && !notificationConfigList.isNotEmpty()) MaterialTheme.colorScheme.surfaceContainerHighest else MaterialTheme.colorScheme.surface
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .padding(horizontal = 16.dp)
    ) {

        HomeScreenCenterLogo(
            tint = logoTint,
            modifier = Modifier
                .size(120.dp)
                .align(Alignment.Center)
        )

        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            HomeScreenNotificationList(
                notificationConfigList = notificationConfigList,
                modifier = Modifier.weight(weight = 1f, fill = false)
            ){ notificationConfig ->
                homeScreenViewModel.deleteOneNotificationConfig(notificationConfig)
            }
            HomeScreenSearchRow(
                state = inputContentState,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = searchRowVerticalPadding,
                        end = searchRowVerticalPadding,
                        bottom = searchRowBottomPadding,
                        top = 14.dp
                    )
                    .imePadding()
                    .align(Alignment.CenterHorizontally)
            ){
                homeScreenViewModel
                    .createOneNotificationConfig(
                        notificationContent = inputContentState.text.toString()
                    )
                inputContentState.clearText()
            }
        }

    }
}