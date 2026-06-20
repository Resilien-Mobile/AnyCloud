package com.baidaidai.anycloud.ui.component.powerScreen

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.unit.dp

@Composable
fun BatteryPercentageComponent(
    modifier: Modifier = Modifier,
    batteryPercentage: Int
){

    val backgroundLayerFloatValue = batteryPercentage / 100.toFloat()

    val liquidLayerSpacer = (-200 + 200 * ( 1 - backgroundLayerFloatValue )).dp

    val infiniteState = rememberInfiniteTransition()
    val rotationDegrees by infiniteState.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 7000,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Restart
        )
    )

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
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.secondaryContainer)
        )

        // Liquid Animation Layer
        Box(
            modifier = Modifier
                .requiredSize(height = 400.dp, width = 400.dp)
                .offset(y = (-100).dp)
                .offset(y = liquidLayerSpacer)
                .rotate(rotationDegrees)
                .clip(RoundedCornerShape(170.dp))
                .background(color = MaterialTheme.colorScheme.surface)
        )

        // Core Display Layer
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = "Battery",
                style = MaterialTheme.typography.titleLargeEmphasized,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
            Text(
                text = "Charging",
                style = MaterialTheme.typography.bodyMediumEmphasized,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )

        }

        // Battery percentage Layer
        Box(
            modifier = Modifier
                .offset(-25.dp,-30.dp)
                .fillMaxSize(),
            contentAlignment = Alignment.BottomEnd
        ) {
            Text(
                text = batteryPercentage.toString(),
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                style = MaterialTheme.typography.displayMedium
            )
        }

    }
}