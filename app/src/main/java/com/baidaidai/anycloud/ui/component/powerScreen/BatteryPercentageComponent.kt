package com.baidaidai.anycloud.ui.component.powerScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun BatteryPercentageComponent(
    modifier: Modifier = Modifier,
    batteryPercentage: Int
){

    val backgroundLayerFloatValue = batteryPercentage / 100.toFloat()

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .border(
                width = 0.1.dp,
                shape = RoundedCornerShape(12.dp),
                color = MaterialTheme.colorScheme.outlineVariant
            ),
        contentAlignment = Alignment.BottomCenter
    ) {
        // Background Layer
        Column(
            modifier = Modifier
                .background(color = Color(0xFFD5F5E3))
                .fillMaxWidth()
                .fillMaxHeight(backgroundLayerFloatValue),
            content = {}
        )

        // Core Display Area
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = "Battery",
                style = MaterialTheme.typography.titleLargeEmphasized
            )
            Text(
                text = "Charging",
                style = MaterialTheme.typography.bodyMediumEmphasized
            )

        }

        // Battery percentage Layer
        Box(
            modifier = Modifier
                .offset(-25.dp,-35.dp)
                .fillMaxSize(),
            contentAlignment = Alignment.BottomEnd
        ) {
            Text(
                text = batteryPercentage.toString(),
                color = Color(0xFF00FF00),
                style = MaterialTheme.typography.displayMedium
            )
        }

    }
}