package com.baidaidai.anycloud.ui.component.intelligent.policyManagerScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.baidaidai.anycloud.domain.clipboard.ClipboardGroup
import com.baidaidai.anycloud.domain.clipboard.PolicyType
import com.baidaidai.anycloud.ui.layout.intelligent.PolicyBottomSheetLayout
import com.baidaidai.anycloud.ui.viewmodel.intelligent.PolicyGroupScreenViewModel
import com.baidaidai.anycloud.ui.viewmodel.intelligent.PolicyManagerScreenViewModel

@Composable
fun PolicyBottomSheetContent(
    modifier: Modifier = Modifier,
    policyManagerScreenViewModel: PolicyManagerScreenViewModel,
    policyGroupScreenViewModel: PolicyGroupScreenViewModel,
    contentPaddingValues: PaddingValues,
    onDismiss: () -> Unit = {},
) {

    val policyManagerUiState by policyManagerScreenViewModel.uiState.collectAsState()
    val policyGroupList by policyGroupScreenViewModel.clipboardGroupList.collectAsState()

    var selectedPolicyType by remember { mutableStateOf(PolicyType.DOMAIN) }
    var selectedPolicyGroup by remember { mutableStateOf<ClipboardGroup?>(policyGroupList.firstOrNull()) }
    val policyContentState = rememberTextFieldState()
    val uriState = rememberTextFieldState()

    PolicyBottomSheetLayout(

        modifier = modifier,
        currentSelectedIndex = policyManagerUiState.currentSelectedIndex,
        onSelectedChange = policyManagerScreenViewModel::updateCurrentSelectedIndex,
        contentPaddingValues = contentPaddingValues,
        content = {

            when(policyManagerUiState.currentSelectedIndex){

                0 -> {
                    InputContent(
                        selectedPolicyType = selectedPolicyType,
                        onSelectedPolicyTypeChange = { policyType ->
                            selectedPolicyType = policyType
                        },
                        selectedPolicyGroup = selectedPolicyGroup,
                        onSelectedPolicyGroupChange = { policyGroup ->
                            selectedPolicyGroup = policyGroup
                        },
                        policyGroupList = policyGroupList,
                        policyContentState = policyContentState,
                        canConfirm = {
                            policyContentState.text.isNotBlank() && selectedPolicyGroup != null
                        },
                        onDismiss = onDismiss,
                        onConfirm = { clipboardPolicy ->
                            policyManagerScreenViewModel.createClipboardPolicy(clipboardPolicy)
                            onDismiss()
                        }
                    )
                }
                else -> {
                    UriContent(
                        uriState = uriState,
                        canConfirm = { uriState.text.isNotBlank() },
                        onDismiss = onDismiss,
                        onConfirm = { uri ->
                            policyManagerScreenViewModel.addClipboardPolicyBundle(uri)
                            onDismiss()
                        }
                    )
                }

            }


        }
    )
}



@PreviewLightDark
@Composable
private fun _preview_() {
    Column(
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.surface)
    ) {
        val clipboardGroup = ClipboardGroup(
            unixTimeStamp = 0L,
            groupName = "Default",
            targetPackageName = "com.example.app"
        )

        PolicyBottomSheetLayout(
            contentPaddingValues = PaddingValues(bottom = 20.dp)
        ) {
            InputContent(
                selectedPolicyType = PolicyType.DOMAIN,
                onSelectedPolicyTypeChange = {},
                selectedPolicyGroup = clipboardGroup,
                onSelectedPolicyGroupChange = {},
                policyGroupList = listOf(clipboardGroup),
                policyContentState = rememberTextFieldState(),
                canConfirm = { false },
                onDismiss = {},
                onConfirm = {}
            )
        }
    }

}
