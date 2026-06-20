package com.baidaidai.anycloud.ui.component.powerScreen

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.baidaidai.anycloud.R

@Composable
fun EnablePowerCloudRow(
    onTrailingButonClick: ()-> Unit
){
    ListItem(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp)),
        leadingContent = {
            Icon(
                painter = painterResource(R.drawable.material_symbols_outline_cloud),
                contentDescription = "Cloud Pattern"
            )
        },
        headlineContent = {
            Text("Enable Power Cloud")
        },
        trailingContent = {
            Button(
                onClick = onTrailingButonClick
            ) {
                Text("3")
            }
        },
        colors = ListItemDefaults.colors().copy(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    )
}