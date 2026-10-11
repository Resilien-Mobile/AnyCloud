package com.baidaidai.anycloud.ui.component.intelligent.clipboardPilotScreen

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.baidaidai.anycloud.ui.R

@Composable
fun GroupBottomSheetContent(
    modifier: Modifier = Modifier,
    contentPaddingValues: PaddingValues,
    onDismiss: () -> Unit = {},
    onConfirm: (
        groupName: String,
        targetPackageName: String
    ) -> Unit = { _, _ -> },
) {
    val groupNameState = rememberTextFieldState()
    val targetPackageNameState = rememberTextFieldState()

    val canConfirm =
        groupNameState.text.isNotBlank() && targetPackageNameState.text.isNotBlank()

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
            text = stringResource(R.string.clipboard_pilot_group_bottom_sheet_title),
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.primary,
        )

        OutlinedTextField(
            state = groupNameState,
            label = {
                Text(stringResource(R.string.clipboard_pilot_group_bottom_sheet_name_label))
            },
            placeholder = {
                Text(stringResource(R.string.clipboard_pilot_group_bottom_sheet_name_placeholder))
            },
            lineLimits = TextFieldLineLimits.SingleLine,
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            state = targetPackageNameState,
            label = {
                Text(stringResource(R.string.clipboard_pilot_group_bottom_sheet_package_name_label))
            },
            placeholder = {
                Text("com.example.app")
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
                Text(stringResource(R.string.clipboard_pilot_group_bottom_sheet_cancel_button))
            }

            Button(
                enabled = canConfirm,
                onClick = {
                    onConfirm(
                        groupNameState.text.toString(),
                        targetPackageNameState.text.toString()
                    )
                }
            ) {
                Text(stringResource(R.string.clipboard_pilot_group_bottom_sheet_confirm_button))
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun _preview_() {
    Column(
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.surface)
    ) {
        GroupBottomSheetContent(
            contentPaddingValues = PaddingValues(bottom = 20.dp)
        )
    }
}
