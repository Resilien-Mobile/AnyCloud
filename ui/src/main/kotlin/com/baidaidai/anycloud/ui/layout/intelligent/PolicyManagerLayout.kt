package com.baidaidai.anycloud.ui.layout.intelligent

import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.isImeVisible
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.baidaidai.anycloud.domain.clipboard.ClipboardError
import com.baidaidai.anycloud.domain.clipboard.ClipboardGroup
import com.baidaidai.anycloud.ui.R
import com.baidaidai.anycloud.ui.component.common.ExpressiveDialog
import com.baidaidai.anycloud.ui.component.intelligent.policyManagerScreen.GroupBottomSheetContent
import com.baidaidai.anycloud.ui.component.intelligent.policyManagerScreen.PolicyBottomSheetContent
import com.baidaidai.anycloud.ui.component.intelligent.policyManagerScreen.PolicyManagerScreenNecessaryComponents
import com.baidaidai.anycloud.ui.navigation.intelligent.PolicyGroupKey
import com.baidaidai.anycloud.ui.navigation.intelligent.PolicyManagerKey
import com.baidaidai.anycloud.ui.screen.intelligent.PolicyGroupScreen
import com.baidaidai.anycloud.ui.screen.intelligent.PolicyManagerScreen
import com.baidaidai.anycloud.ui.theme.AnyCloudTheme
import com.baidaidai.anycloud.ui.viewmodel.intelligent.PolicyGroupScreenViewModel
import com.baidaidai.anycloud.ui.viewmodel.intelligent.PolicyManagerScreenViewModel
import kotlinx.coroutines.flow.collect
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
    var clipboardPolicyBundleError by remember { mutableStateOf<ClipboardError?>(null) }
    val bottomSheetState = rememberBottomSheetState(
        initialValue = SheetValue.Hidden,
        enabledValues = setOf(
            SheetValue.Hidden,
            SheetValue.Expanded
        )
    )
    val isImeVisible = WindowInsets.isImeVisible

    LaunchedEffect(policyManagerScreenViewModel) {
        policyManagerScreenViewModel.clipboardPolicyBundleError.collect { error ->
            clipboardPolicyBundleError = error
        }
    }

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
                        contentDescription = stringResource(R.string.policy_manager_screen_edit_content_description)
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
                                is PolicyGroupKey -> Text(stringResource(R.string.policy_manager_screen_policy_group_title))
                                is PolicyManagerKey -> Text(stringResource(R.string.policy_manager_screen_policy_manager_title))
                            }
                        }
                    )
            }
        ) { contentPadding ->

            clipboardPolicyBundleError?.let { clipboardPolicyBundleError ->
                ExpressiveDialog(
                    onDismissRequest = {},
                    confirmButton = {
                        Button(
                            onClick = {
                                policyManagerScreenViewModel.dismissClipboardPolicyBundleError()
                            }
                        ) {
                            Text(stringResource(R.string.policy_manager_error_dialog_confirm_button))
                        }
                    },
                    icon = {
                        Icon(
                            painter = painterResource(R.drawable.material_symbols_error),
                            contentDescription = null
                        )
                    },
                    title = {
                        Text(clipboardPolicyBundleError.errorMessage)
                    },
                    enableSubTitle = clipboardPolicyBundleError.errorCompanion != null,
                    subtitle = {
                        clipboardPolicyBundleError.errorCompanion?.let { companion ->
                            Text(companion)
                        }
                    },
                    enableContent = clipboardPolicyBundleError.errorCompanion != null,
                    content = {
                        Column(
                            modifier = Modifier
                                .heightIn(max = 300.dp)
                                .verticalScroll(rememberScrollState())
                        ) {
                            Text(clipboardPolicyBundleError.errorCause)
                        }
                    }
                )
            }

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
                                policyGroupScreenViewModel = policyGroupScreenViewModel,
                                policyManagerScreenViewModel = policyManagerScreenViewModel,
                                onDismiss = {
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
