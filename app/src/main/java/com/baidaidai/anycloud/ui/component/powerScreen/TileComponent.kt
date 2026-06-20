package com.baidaidai.anycloud.ui.component.powerScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp

@Composable
fun TileComponent(
    modifier: Modifier = Modifier,
    headlineContent: String,
    content: @Composable ()-> Unit
){

    val cardColors = CardDefaults.cardColors().copy(
        containerColor = MaterialTheme.colorScheme.surfaceContainer,
        contentColor = MaterialTheme.colorScheme.onSurface
    )

    Card(
        modifier = modifier,
        colors = cardColors
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    text = headlineContent,
                    style = MaterialTheme.typography.titleSmall
                )
            }
            content()
        }
    }
}

@PreviewLightDark
@Composable
private fun _preview_(){
    TileComponent(
        headlineContent = "Watt",
        modifier = Modifier
            .size(200.dp,100.dp),
    ){
        Row(
            modifier = Modifier
                .fillMaxSize(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = String.format("%.2f", 7.99),
                style = MaterialTheme.typography.titleLarge
            )
        }
    }
}