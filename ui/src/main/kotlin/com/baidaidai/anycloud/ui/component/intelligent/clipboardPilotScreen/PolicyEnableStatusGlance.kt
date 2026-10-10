package com.baidaidai.anycloud.ui.component.intelligent.clipboardPilotScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp

@Composable
fun PolicyEnableStatusGlance(
    modifier: Modifier = Modifier,
    enabledPolicyCount: Int = 0,
    totalPolicyCount: Int = 0,
    builtInPolicyEnabledCount: Int = 0
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .padding(top = 18.dp)
    ) {
        PolicyEnableStatusGlanceSlot(
            label = "策略组数",
            value = enabledPolicyCount,
            modifier = Modifier.weight(1f)
        )

        VerticalDivider(
            modifier = Modifier
                .fillMaxHeight()
        )

        PolicyEnableStatusGlanceSlot(
            label = "策略总数",
            value = totalPolicyCount,
            modifier = Modifier.weight(1f)
        )

        VerticalDivider(
            modifier = Modifier
                .fillMaxHeight()
        )

        PolicyEnableStatusGlanceSlot(
            label = "内置策略",
            value = builtInPolicyEnabledCount,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun PolicyEnableStatusGlanceSlot(
    modifier: Modifier = Modifier,
    label: String,
    value: Int
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier.padding(horizontal = 8.dp)
    ) {
        Text(
            text = value.toString(),
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.W700,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.primary
        )

        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@PreviewLightDark
@Composable
private fun _PolicyEnableStatusGlancePreview_() {
    PolicyEnableStatusGlance(
        enabledPolicyCount = 6,
        totalPolicyCount = 12,
        builtInPolicyEnabledCount = 4
    )
}
