package com.baidaidai.anycloud.ui.component.intelligent.clipboardPilotScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
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
import com.baidaidai.anycloud.domain.clipboard.ClipboardPolicy
import com.baidaidai.anycloud.domain.clipboard.PolicyType

@Composable
fun PolicyBottomSheetContent(
    modifier: Modifier = Modifier,
    policyGroupList: List<String> = listOf(
        "Default",
        "Direct",
        "Proxy",
    ),
    contentPaddingValues: PaddingValues,
    onDismiss: () -> Unit = {},
    onConfirm: (ClipboardPolicy) -> Unit = {},
) {
    var selectedPolicyType by remember { mutableStateOf(PolicyType.DOMAIN) }
    var selectedPolicyGroup by remember { mutableStateOf<String?>(policyGroupList.firstOrNull()) }
    val policyContentState = rememberTextFieldState()

    val canConfirm = policyContentState.text.isNotBlank()

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
        Text(
            text = "Policy",
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.primary,
        )

        SelectableTextField(
            label = "分流规则",
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
            label = "分流策略",
            selectedOption = selectedPolicyGroup ?: "None",
            optionList = listOf("None") + policyGroupList,
            optionText = { policyGroup ->
                policyGroup
            },
            onOptionSelected = { policyGroup ->
                selectedPolicyGroup = if (policyGroup == "None") {
                    null
                } else {
                    policyGroup
                }
            }
        )

        Spacer(modifier = Modifier.height(2.dp))

        Text(
            text = "Content",
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.primary,
        )

        OutlinedTextField(
            state = policyContentState,
            label = {
                Text("匹配正文")
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
                Text("Cancel")
            }

            Button(
                enabled = canConfirm,
                onClick = {
                    val clipboardPolicy = ClipboardPolicy(
                        unixTimeStamp = System.currentTimeMillis(),
                        policyType = selectedPolicyType,
                        policyContent = policyContentState.text.toString(),
                        policyGroup = selectedPolicyGroup
                    )

                    onConfirm(clipboardPolicy)
                }
            ) {
                Text("Confirm")
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
