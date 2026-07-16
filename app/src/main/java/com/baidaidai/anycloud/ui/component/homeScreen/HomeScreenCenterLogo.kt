package com.baidaidai.anycloud.ui.component.homeScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.baidaidai.anycloud.R

@Composable
fun HomeScreenCenterLogo(
    modifier: Modifier = Modifier,
    tint: Color = LocalContentColor.current
){
    Icon(
        painter = painterResource(R.drawable.material_symbols_cloud),
        contentDescription = "AnyCloud's Logo",
        tint = tint,
        modifier = modifier
    )
}

@PreviewLightDark
@Composable
private fun _preview_() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        HomeScreenCenterLogo(
            tint = MaterialTheme.colorScheme.surfaceContainerLowest,
            modifier = Modifier
                .size(120.dp)
                .align(Alignment.Center)
        )
    }
}