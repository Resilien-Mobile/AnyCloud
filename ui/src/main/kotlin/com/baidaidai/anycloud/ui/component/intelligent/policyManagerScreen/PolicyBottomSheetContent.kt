package com.baidaidai.anycloud.ui.component.intelligent.policyManagerScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.baidaidai.anycloud.domain.clipboard.ClipboardGroup
import com.baidaidai.anycloud.domain.clipboard.ClipboardPolicy
import com.baidaidai.anycloud.domain.clipboard.PolicyType
import com.baidaidai.anycloud.ui.R
import androidx.compose.ui.res.stringResource

@Composable
fun PolicyBottomSheetContent(
    modifier: Modifier = Modifier,
    currentSelectedIndex: Int = 0,
    onSelectedChange: (index: Int) -> Unit = {},
    policyGroupList: List<ClipboardGroup> = emptyList(),
    contentPaddingValues: PaddingValues,
    onDismiss: () -> Unit = {},
    onConfirm: (ClipboardPolicy) -> Unit = {},
) {
    var selectedPolicyType by remember { mutableStateOf(PolicyType.DOMAIN) }
    var selectedPolicyGroup by remember { mutableStateOf<ClipboardGroup?>(policyGroupList.firstOrNull()) }
    val policyContentState = rememberTextFieldState()

    val canConfirm = policyContentState.text.isNotBlank() && selectedPolicyGroup != null
    val noGroupText = stringResource(R.string.clipboard_pilot_policy_bottom_sheet_no_group)

    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = modifier
            .fillMaxWidth()
            .padding(
                start = 16.dp,
                top = 16.dp,
                end = 16.dp,
                bottom = contentPaddingValues.calculateBottomPadding()
            )
    ) {

        ImportMethodButtonGroup(
            currentSelectedIndex = currentSelectedIndex,
            onSelectedChange = onSelectedChange
        )

        Text(
            text = stringResource(R.string.clipboard_pilot_policy_bottom_sheet_title),
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.primary,
        )

        SelectableTextField(
            label = stringResource(R.string.clipboard_pilot_policy_bottom_sheet_policy_type_label),
            selectedOption = selectedPolicyType,
            optionList = PolicyType.entries,
            optionText = { policyType ->
                policyType.name
            },
            onOptionSelected = { policyType ->
                selectedPolicyType = policyType
            }
        )

        SelectableTextField(
            label = stringResource(R.string.clipboard_pilot_policy_bottom_sheet_policy_group_label),
            selectedOption = selectedPolicyGroup,
            optionList = listOf<ClipboardGroup?>(null) + policyGroupList,
            optionText = { policyGroup ->
                policyGroup?.groupName ?: noGroupText
            },
            onOptionSelected = { policyGroup ->
                selectedPolicyGroup = policyGroup
            }
        )

        Spacer(modifier = Modifier.height(2.dp))

        Text(
            text = stringResource(R.string.clipboard_pilot_policy_bottom_sheet_content_title),
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.primary,
        )

        OutlinedTextField(
            state = policyContentState,
            label = {
                Text(stringResource(R.string.clipboard_pilot_policy_bottom_sheet_content_label))
            },
            placeholder = {
                Text(selectedPolicyType.toPlaceholder())
            },
            lineLimits = TextFieldLineLimits.SingleLine,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            horizontalArrangement = Arrangement.End,
            modifier = Modifier.fillMaxWidth()
        ) {
            TextButton(
                onClick = onDismiss
            ) {
                Text(stringResource(R.string.clipboard_pilot_policy_bottom_sheet_cancel_button))
            }

            Button(
                enabled = canConfirm,
                onClick = {
                    val currentPolicyGroup = selectedPolicyGroup ?: return@Button
                    val clipboardPolicy = ClipboardPolicy(
                        unixTimeStamp = 0L,
                        policyType = selectedPolicyType,
                        policyContent = policyContentState.text.toString(),
                        policyGroup = currentPolicyGroup.unixTimeStamp,
                        targetPackageName = currentPolicyGroup.targetPackageName
                    )

                    onConfirm(clipboardPolicy)
                }
            ) {
                Text(stringResource(R.string.clipboard_pilot_policy_bottom_sheet_confirm_button))
            }
        }
    }
}

private fun PolicyType.toPlaceholder(): String {
    val placeholder = when (this) {
        PolicyType.IP_CIDR -> "192.168.0.0/16"
        PolicyType.DOMAIN -> "example.com"
        PolicyType.DOMAIN_SUFFIX -> ".example.com"
    }

    return placeholder
}

@PreviewLightDark
@Composable
private fun _preview_() {
    Column(
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.surface)
    ) {
        PolicyBottomSheetContent(
            contentPaddingValues = PaddingValues(bottom = 20.dp)
        )
    }

}
