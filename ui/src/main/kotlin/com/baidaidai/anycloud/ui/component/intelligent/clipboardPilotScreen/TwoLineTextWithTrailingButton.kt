package com.baidaidai.anycloud.ui.component.intelligent.clipboardPilotScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.baidaidai.anycloud.ui.R
import com.baidaidai.anycloud.ui.theme.getPrimaryIconButtonColors

@Composable
fun TwoLineTextWithTrailingButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .fillMaxWidth()
            .height(
                intrinsicSize = IntrinsicSize.Min
            )
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = "Policy Manager",
                style = MaterialTheme.typography.titleMedium,
                fontSize = 24.sp,
                lineHeight = 16.sp,
                fontWeight = FontWeight.W600
            )
            Text(
                text = "配置 Pilot 规则与默认规则集",
                style = MaterialTheme.typography.bodySmall,
                fontSize = 14.sp,
                lineHeight = 16.sp,
                fontWeight = FontWeight.W400
            )
        }

        IconButton(
            // 需要更 Expressive 的图案
            shape = RoundedCornerShape(10.dp),
            onClick = onClick,
            colors = getPrimaryIconButtonColors(),
            modifier = Modifier
                .aspectRatio(1f)
                .fillMaxHeight()
        ) {
            Icon(
                painter = painterResource(R.drawable.material_symbols_fork_right),
                contentDescription = null
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun _TwoLineTextWithTrailingButtonPreview_() {
    TwoLineTextWithTrailingButton(
        modifier = Modifier
            .background(
                color = MaterialTheme.colorScheme.surface
            )
    )
}
