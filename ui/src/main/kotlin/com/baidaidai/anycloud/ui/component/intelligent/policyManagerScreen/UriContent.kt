package com.baidaidai.anycloud.ui.component.intelligent.policyManagerScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
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
import com.baidaidai.anycloud.ui.R

@Composable
internal fun UriContent(
    uriState: TextFieldState,
    canConfirm: () -> Boolean,
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit,
) {
    Text(
        text = stringResource(R.string.clipboard_pilot_policy_bottom_sheet_title),
        style = MaterialTheme.typography.labelLarge,
        color = MaterialTheme.colorScheme.primary,
    )

    OutlinedTextField(
        state = uriState,
        label = {
            Text("URI")
        },
        lineLimits = TextFieldLineLimits.SingleLine,
        modifier = Modifier.fillMaxWidth()
    )

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
            enabled = canConfirm(),
            onClick = {
                onConfirm(uriState.text.toString())
            }
        ) {
            Text(stringResource(R.string.clipboard_pilot_policy_bottom_sheet_confirm_button))
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
        val uriState = rememberTextFieldState()

        UriContent(
            uriState = uriState,
            canConfirm = { uriState.text.isNotBlank() },
            onDismiss = {},
            onConfirm = {}
        )
    }
}
