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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.baidaidai.anycloud.domain.clipboard.ClipboardPolicy
import com.baidaidai.anycloud.ui.R
import com.baidaidai.anycloud.ui.component.intelligent.clipboardPilotScreen.PolicyBottomSheetContent
import com.baidaidai.anycloud.ui.screen.intelligent.PolicyManagerScreen
import com.baidaidai.anycloud.ui.theme.AnyCloudTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun PolicyManagerLayout() {

    // States
    val bottomSheetState = rememberBottomSheetState(
        initialValue = SheetValue.Hidden,
        enabledValues = setOf(
            SheetValue.Hidden,
            SheetValue.Expanded
        )
    )
    val isBottomSheetVisible = bottomSheetState.isVisible
    val isImeVisible = WindowInsets.isImeVisible

    val coroutineScope = rememberCoroutineScope()

    // Values
    val fakeList = listOf(ClipboardPolicy(1L, policyContent = "", policyGroup = "Test"))

    // Launched
    LaunchedEffect(isImeVisible, isBottomSheetVisible) {
        if (!isBottomSheetVisible) return@LaunchedEffect

        if (isImeVisible) {
            bottomSheetState.expand()
        }
    }

    AnyCloudTheme {
        Scaffold(
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {
                        coroutineScope.launch {
                            bottomSheetState.expand()
                        }
                    }
                ) {
                    Icon(
                        painter = painterResource(R.drawable.material_symbols_edit),
                        contentDescription = "edit"
                    )
                }
            },
            topBar = {
                TopAppBar(
                    title = {
                        Text("Policy Manager")
                    }
                )
            }
        ) { contentPadding ->

            if(bottomSheetState.isVisible){
                ModalBottomSheet(
                    sheetState = bottomSheetState,
                    properties = ModalBottomSheetProperties(
                        shouldDismissOnBackPress = !isImeVisible
                    ),
                    onDismissRequest = {
                        coroutineScope.launch {
                            bottomSheetState.hide()
                        }
                    },
                    modifier = Modifier.imePadding()
                ) {
                    PolicyBottomSheetContent(
                        contentPaddingValues = contentPadding
                    )
                }
            }

            PolicyManagerScreen(
                contentPadding = contentPadding,
                clipboardPolicyList = fakeList,
            )

        }
    }
}
