package com.baidaidai.anycloud.ui.layout

import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.baidaidai.anycloud.domain.clipboard.ClipboardGroup
import com.baidaidai.anycloud.ui.R
import com.baidaidai.anycloud.ui.component.intelligent.clipboardPilotScreen.GroupBottomSheetContent
import com.baidaidai.anycloud.ui.component.intelligent.clipboardPilotScreen.PolicyBottomSheetContent
import com.baidaidai.anycloud.ui.component.intelligent.policyManagerScreen.PolicyManagerScreenNecessaryComponents
import com.baidaidai.anycloud.ui.navigation.intelligent.PolicyGroupKey
import com.baidaidai.anycloud.ui.navigation.intelligent.PolicyManagerKey
import com.baidaidai.anycloud.ui.screen.intelligent.PolicyGroupScreen
import com.baidaidai.anycloud.ui.screen.intelligent.PolicyManagerScreen
import com.baidaidai.anycloud.ui.theme.AnyCloudTheme
import com.baidaidai.anycloud.ui.viewmodel.intelligent.PolicyGroupScreenViewModel
import com.baidaidai.anycloud.ui.viewmodel.intelligent.PolicyManagerScreenViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun PolicyManagerLayout() {

    // Navigation
    val navigationBackStack = rememberNavBackStack(PolicyManagerKey)
    val currentDestination = navigationBackStack.last()

    // ViewModel
    val policyManagerScreenViewModel = hiltViewModel<PolicyManagerScreenViewModel>()
    val policyGroupScreenViewModel = hiltViewModel<PolicyGroupScreenViewModel>()

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
    val clipboardGroupList by policyGroupScreenViewModel.clipboardGroupList.collectAsState()
    val policyGroupNameList = clipboardGroupList
        .map { clipboardGroup ->
            clipboardGroup.groupName
        }
        .distinct() // 不会长久存在，因为即使主键不同，后续只要存在相同名称的Group自动顶掉替换

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
                PolicyManagerScreenNecessaryComponents
                    .PolicyManagerScreenTopAppBar(
                        onSwitchClick = {
                            navigationBackStack.removeLastOrNull()
                            when (currentDestination) {
                                is PolicyGroupKey -> navigationBackStack.add(PolicyManagerKey)
                                is PolicyManagerKey -> navigationBackStack.add(PolicyGroupKey)
                            }
                        },
                        titleContent = {
                            when (currentDestination) {
                                is PolicyGroupKey -> Text("Policy Group")
                                is PolicyManagerKey -> Text("Policy Manager")
                            }
                        }
                    )
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
                    when (currentDestination) {
                        is PolicyGroupKey -> {
                            GroupBottomSheetContent(
                                contentPaddingValues = contentPadding,
                                onDismiss = {
                                    hideBottomSheet()
                                },
                                onConfirm = { groupName, targetPackageName ->
                                    val clipboardGroup = ClipboardGroup(
                                        unixTimeStamp = 0L,
                                        groupName = groupName,
                                        targetPackageName = targetPackageName
                                    )

                                    policyGroupScreenViewModel.createClipboardGroup(
                                        clipboardGroup = clipboardGroup
                                    )
                                    hideBottomSheet()
                                }
                            )
                        }

                        is PolicyManagerKey -> {
                            PolicyBottomSheetContent(
                                contentPaddingValues = contentPadding,
                                policyGroupList = policyGroupNameList,
                                onDismiss = {
                                    hideBottomSheet()
                                },
                                onConfirm = { clipboardPolicy ->
                                    policyManagerScreenViewModel.createClipboardPolicy(
                                        clipboardPolicy = clipboardPolicy
                                    )
                                    hideBottomSheet()
                                }
                            )
                        }
                    }
                }
            }

            NavDisplay(
                backStack = navigationBackStack,
                modifier = Modifier.fillMaxSize(),
                onBack = {
                    navigationBackStack.removeLastOrNull()
                },
                entryProvider = entryProvider {

                    entry<PolicyManagerKey> {
                        PolicyManagerScreen(
                            contentPadding = contentPadding
                        )
                    }

                    entry<PolicyGroupKey>{
                        PolicyGroupScreen(
                            contentPadding = contentPadding
                        )
                    }

                }
            )
        }
    }
}
