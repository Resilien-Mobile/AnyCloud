package com.baidaidai.anycloud.ui.component.homeScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.baidaidai.anycloud.R
import com.baidaidai.anycloud.domain.notification.model.NotificationConfig

@Composable
fun HomeScreenNotificationList(
    modifier: Modifier = Modifier,
    notificationConfigList: List<NotificationConfig>,
    onDeleteNotification: (notificationConfig: NotificationConfig)-> Unit = {},
    onObverseTaskStatus: (notificationConfig: NotificationConfig) -> Unit = {}
){


    val listItemContainerColor = MaterialTheme.colorScheme.surfaceContainerLow
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


    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
    ) {
        itemsIndexed(
            items = notificationConfigList,
            key = { index, notificationConfig -> notificationConfig.unixTimeStamp }
        ){ index, notificationConfig ->

            val swipeToDismissBoxState = rememberSwipeToDismissBoxState()

            SwipeToDismissBox(
                state = swipeToDismissBoxState,
                backgroundContent = {},
                enableDismissFromStartToEnd = false,
                onDismiss = { swipeToDismissBoxValue ->
                    if (swipeToDismissBoxValue == SwipeToDismissBoxValue.EndToStart) {
                        onDeleteNotification(notificationConfig)
                    }
                }
            ) {
                ListItem(
                    overlineContent = {
                        Text(notificationConfig.notificationTitle)
                    },
                    headlineContent = { Text(notificationConfig.notificationContent) },
                    leadingContent = { Icon(painterResource(R.drawable.material_symbols_cloud),null) },
                    trailingContent = {
                        Checkbox(
                            checked = notificationConfig.isTaskFinished,
                            onCheckedChange = { onObverseTaskStatus(notificationConfig) }
                        )
                    },
                    colors = listItemColors,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(4.dp)),
                )
            }
            if (index != notificationConfigList.lastIndex) { Spacer(modifier = Modifier.height(2.dp)) }
        }
    }
}

@PreviewLightDark
@Composable
private fun _preview_() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = MaterialTheme.colorScheme.surface
            )
            .padding(16.dp)
    ) {
        HomeScreenNotificationList(
            notificationConfigList = listOf(NotificationConfig(), NotificationConfig()),
            modifier = Modifier
                .weight(
                    weight = 1f,
                    fill = false
                )
        )
    }
}
