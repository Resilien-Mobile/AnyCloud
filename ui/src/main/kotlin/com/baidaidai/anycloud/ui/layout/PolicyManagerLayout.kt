package com.baidaidai.anycloud.ui.layout

import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.isImeVisible
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalBottomSheetProperties
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.baidaidai.anycloud.ui.R
import com.baidaidai.anycloud.ui.component.intelligent.clipboardPilotScreen.PolicyBottomSheetContent
import com.baidaidai.anycloud.ui.component.intelligent.policyManagerScreen.PolicyManagerScreenNecessaryComponents
import com.baidaidai.anycloud.ui.screen.intelligent.PolicyManagerScreen
import com.baidaidai.anycloud.ui.theme.AnyCloudTheme
import com.baidaidai.anycloud.ui.viewmodel.intelligent.PolicyManagerScreenViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun PolicyManagerLayout() {

    // ViewModel
    val policyManagerScreenViewModel = hiltViewModel<PolicyManagerScreenViewModel>()

    // States
    var shouldShowBottomSheet by remember { mutableStateOf(false) }
    val bottomSheetState = rememberBottomSheetState(
        initialValue = SheetValue.Hidden,
        enabledValues = setOf(
            SheetValue.Hidden,
            SheetValue.Expanded
        )
    )
    val isImeVisible = WindowInsets.isImeVisible

    val coroutineScope = rememberCoroutineScope()
    fun hideBottomSheet() {
        coroutineScope.launch {
            bottomSheetState.hide()
        }.invokeOnCompletion {
            if (!bottomSheetState.isVisible) {
                shouldShowBottomSheet = false
            }
        }
    }

    // Values

    // Launched
    LaunchedEffect(isImeVisible, shouldShowBottomSheet) {
        if (!shouldShowBottomSheet) return@LaunchedEffect

        if (isImeVisible) {
            bottomSheetState.expand()
        }
    }

    AnyCloudTheme {
        Scaffold(
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {
                        shouldShowBottomSheet = true
                    }
                ) {
                    Icon(
                        painter = painterResource(R.drawable.material_symbols_edit),
                        contentDescription = "edit"
                    )
                }
            },
            topBar = {
                PolicyManagerScreenNecessaryComponents.PolicyManagerScreenTopAppBar {  }
            }
        ) { contentPadding ->

            if (shouldShowBottomSheet) {
                ModalBottomSheet(
                    sheetState = bottomSheetState,
                    properties = ModalBottomSheetProperties(
                        shouldDismissOnBackPress = !isImeVisible
                    ),
                    onDismissRequest = {
                        hideBottomSheet()
                    },
                    modifier = Modifier.imePadding()
                ) {
                    PolicyBottomSheetContent(
                        contentPaddingValues = contentPadding,
                        onDismiss = {
                            coroutineScope.launch {
                                bottomSheetState.hide()
                            }
                        },
                        onConfirm = { clipboardPolicy ->
                            policyManagerScreenViewModel.createClipboardPolicy(
                                clipboardPolicy = clipboardPolicy
                            )
                            coroutineScope.launch {
                                bottomSheetState.hide()
                            }
                        }
                    )
                }
            }

            PolicyManagerScreen(
                contentPadding = contentPadding
            )

        }
    }
}
