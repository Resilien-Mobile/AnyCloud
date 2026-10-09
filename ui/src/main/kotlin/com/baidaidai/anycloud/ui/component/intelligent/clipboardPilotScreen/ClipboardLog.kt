package com.baidaidai.anycloud.ui.component.intelligent.clipboardPilotScreen

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.baidaidai.anycloud.domain.clipboard.ClipboardLog
import com.baidaidai.anycloud.ui.R
import com.baidaidai.anycloud.ui.theme.getListItemColors

@Composable
fun ClipboardLog(
    clipboardLogList: List<ClipboardLog?>,
    modifier: Modifier = Modifier,
) {

    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .clip(
                RoundedCornerShape(12.dp)
            )
    ) {
        itemsIndexed(
            items = clipboardLogList
        ) { index, clipboardLog ->
            if (clipboardLog == null) {
                // 占位空行
                ListItem(
                    colors = getListItemColors(),
                    headlineContent = { Text("") },
                    supportingContent = { Text("") }
                )
            } else {
                ListItem(
                    colors = getListItemColors(),
                    leadingContent = {
                        Icon(
                            painter = painterResource(
                                if (clipboardLog.isMatchedResult) {
                                    R.drawable.material_symbols_check_circle
                                } else {
                                    R.drawable.material_symbols_error
                                }
                            ),
                            contentDescription = null,
                            tint = if (clipboardLog.isMatchedResult) {
                                MaterialTheme.colorScheme.primary
                            } else {
                                MaterialTheme.colorScheme.error
                            }
                        )
                    },
                    headlineContent = {
                        Text(
                            text = clipboardLog.host.ifBlank { "Unknown Host" }
                        )
                    },
                    supportingContent = {
                        Text(
                            text = if (clipboardLog.isMatchedResult) {
                                "Matched"
                            } else {
                                "Not Matched"
                            }
                        )
                    }
                )
            }
            if (index != clipboardLogList.size - 1) {
                HorizontalDivider(
                    thickness = 1.dp,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }

}

@PreviewLightDark
@Composable
private fun _preview_() {
    ClipboardLog(
        clipboardLogList = listOf(
            ClipboardLog(
                unixTime = 0L,
                host = "www.xiaohongshu.com",
                isMatchedResult = true
            ),
            ClipboardLog(
                unixTime = 0L,
                host = "example.com",
                isMatchedResult = false
            ),
            null,
            null
        )
    )
}
