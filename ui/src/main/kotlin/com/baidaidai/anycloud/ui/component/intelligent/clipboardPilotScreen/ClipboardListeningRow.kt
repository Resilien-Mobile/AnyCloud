package com.baidaidai.anycloud.ui.component.intelligent.clipboardPilotScreen

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.baidaidai.anycloud.ui.R
import com.baidaidai.anycloud.ui.theme.getListItemColors

@Composable
fun ClipboardListeningRow(
    isClipboardListening: Boolean,
    onStartClipboardListening: () -> Unit,
    onStopClipboardListening: () -> Unit
) {
    ListItem(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp)),
        leadingContent = {
            Icon(
                painter = painterResource(R.drawable.material_symbols_visibility),
                contentDescription = stringResource(R.string.clipboard_pilot_screen_clipboard_listening_content_description)
            )
        },
        headlineContent = {
            Text(stringResource(R.string.clipboard_pilot_screen_clipboard_listening_title))
        },
        trailingContent = {
            Switch(
                checked = isClipboardListening,
                onCheckedChange = { isListening ->
                    if (isListening) {
                        onStartClipboardListening()
                    } else {
                        onStopClipboardListening()
                    }
                }
            )
        },
        colors = getListItemColors()
    )
}

@PreviewLightDark
@Composable
private fun _Preview_() {
    ClipboardListeningRow(
        isClipboardListening = false,
        onStartClipboardListening = {},
        onStopClipboardListening = {}
    )
}
