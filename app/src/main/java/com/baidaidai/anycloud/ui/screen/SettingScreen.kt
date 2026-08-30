package com.baidaidai.anycloud.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.baidaidai.anycloud.R
import com.baidaidai.anycloud.ui.theme.AnyCloudTheme

@Composable
fun SettingScreen(
    innerPadding: PaddingValues,
    modifier: Modifier = Modifier
){

    val listItemContainerColor = MaterialTheme.colorScheme.surfaceContainer
    val listItemContentColor = MaterialTheme.colorScheme.onPrimaryContainer
    val listItemColors = ListItemColors(
        containerColor = listItemContainerColor,
        contentColor = listItemContentColor,
        leadingContentColor = listItemContentColor,
        trailingContentColor = listItemContentColor,
        overlineContentColor = listItemContentColor,
        supportingContentColor = listItemContentColor,
        disabledContainerColor = listItemContainerColor,
        disabledContentColor = listItemContentColor,
        disabledLeadingContentColor = listItemContentColor,
        disabledTrailingContentColor = listItemContentColor,
        disabledOverlineContentColor = listItemContentColor,
        disabledSupportingContentColor = listItemContentColor,
        selectedContainerColor = listItemContainerColor,
        selectedContentColor = listItemContentColor,
        selectedLeadingContentColor = listItemContentColor,
        selectedTrailingContentColor = listItemContentColor,
        selectedOverlineContentColor = listItemContentColor,
        selectedSupportingContentColor = listItemContentColor,
        draggedContainerColor = listItemContainerColor,
        draggedContentColor = listItemContentColor,
        draggedLeadingContentColor = listItemContentColor,
        draggedTrailingContentColor = listItemContentColor,
        draggedOverlineContentColor = listItemContentColor,
        draggedSupportingContentColor = listItemContentColor
    )


    Column(modifier = modifier.fillMaxSize().padding(innerPadding).padding(horizontal = 16.dp)) {

        // Cloud Display Setting
        ListItem(
            leadingContent = {
                Icon(
                    painter = painterResource(R.drawable.material_symbols_compare_arrows),
                    contentDescription = null
                )
            },
            trailingContent = {
                Switch(
                    checked = false,
                    onCheckedChange = {}
                )
            },
            headlineContent = {
                Text("Switch Ongoing Style")
            },
            supportingContent = {
                Text("Avoid having the OEM display the App Name, but instead show the remaining task quantity")
            },
            colors = listItemColors,
            modifier = Modifier.clip(RoundedCornerShape(16.dp))
        )

        // Behavior Setting


        // Artificial Intelligent Setting


        // Permission Settings ( Shizuku & Notification )

    }


}

@PreviewLightDark
@Composable
private fun _preview_() {
    AnyCloudTheme {
        Scaffold { contentPadding ->
            SettingScreen(
                innerPadding = contentPadding,
                modifier = Modifier
                    .background(color = MaterialTheme.colorScheme.surface)
                    .padding(12.dp)
            )
        }
    }
}