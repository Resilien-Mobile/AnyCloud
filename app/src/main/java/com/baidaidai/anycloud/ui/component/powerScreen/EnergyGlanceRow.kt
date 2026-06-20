package com.baidaidai.anycloud.ui.component.powerScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp

@Composable
fun EnergyGlanceRow(
    firstTileHeadlineContent: String,
    firstTileContent:@Composable ()-> Unit = {},
    secondTileHeadlineContent: String,
    secondTileContent:@Composable ()-> Unit = {},
    batteryPercentage: Int
){
    Row(
        modifier = Modifier
            .height(200.dp)
    ) {

        BatteryPercentageComponent(
            modifier = Modifier.weight(1f),
            batteryPercentage = batteryPercentage
        )

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {

            TileComponent(
                modifier = Modifier.weight(1f),
                headlineContent = firstTileHeadlineContent,
                content = firstTileContent
            )

            Spacer(modifier = Modifier.height(12.dp))

            TileComponent(
                modifier = Modifier.weight(1f),
                headlineContent = secondTileHeadlineContent,
                content = secondTileContent
            )
        }

    }
}

@PreviewLightDark
@Composable
private fun _preview_(){
    EnergyGlanceRow(
        batteryPercentage = 70,
        firstTileHeadlineContent = "Adapter Type",
        secondTileHeadlineContent = "Current Watt",

        secondTileContent = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = String.format("%.2f", 1.77),
                    style = MaterialTheme.typography.displaySmall
                )
            }
        }
    )
}