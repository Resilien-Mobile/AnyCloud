package com.baidaidai.anycloud.ui.component.homeScreen

import androidx.compose.animation.core.animateOffsetAsState
import androidx.compose.animation.core.snap
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.baidaidai.anycloud.R
import com.baidaidai.anycloud.domain.notification.model.NotificationConfig

@Composable
fun HomeScreenNotificationList(
    modifier: Modifier = Modifier,
    notificationConfigList: List<NotificationConfig>,
    contentPadding: PaddingValues = PaddingValues(),
    onDeleteNotification: (notificationConfig: NotificationConfig)-> Unit = {},
    onObverseTaskStatus: (notificationConfig: NotificationConfig) -> Unit = {},
    onNotificationDrag: () -> Unit = {},
    onNotificationDragEnd: (
        notificationConfig: NotificationConfig,
        insertionIndex: Int
    ) -> Unit = { _, _ -> }
){
    val lazyListState = rememberLazyListState()

    LazyColumn(
        state = lazyListState,
        contentPadding = contentPadding,
        modifier = modifier
            .fillMaxWidth()
    ) {
        itemsIndexed(
            items = notificationConfigList,
            key = { index, notificationConfig -> notificationConfig.unixTimeStamp }
        ){ index, notificationConfig ->

            val swipeToDismissBoxState = rememberSwipeToDismissBoxState()
            var isDragging by remember(notificationConfig.unixTimeStamp) {
                mutableStateOf(false)
            }
            var dragOffset by remember(notificationConfig.unixTimeStamp) {
                mutableStateOf(Offset.Zero)
            }
            val animatedDragOffset by animateOffsetAsState(
                targetValue = dragOffset,
                animationSpec = if (isDragging) snap() else spring(),
                label = "notificationDragOffset"
            )

            val listItemShape = when {
                isDragging || notificationConfigList.size == 1 ->
                    RoundedCornerShape(16.dp)

                index == 0 ->
                    RoundedCornerShape(
                        topStart = 16.dp,
                        topEnd = 16.dp,
                        bottomStart = 4.dp,
                        bottomEnd = 4.dp
                    )

                index == notificationConfigList.lastIndex ->
                    RoundedCornerShape(
                        topStart = 4.dp,
                        topEnd = 4.dp,
                        bottomStart = 16.dp,
                        bottomEnd = 16.dp
                    )

                else -> RoundedCornerShape(4.dp)
            }
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

            SwipeToDismissBox(
                modifier = Modifier
                    .zIndex(if (isDragging) 1f else 0f)
                    .graphicsLayer {
                        translationX = animatedDragOffset.x
                        translationY = animatedDragOffset.y
                        shadowElevation = if (isDragging) 12.dp.toPx() else 0f
                        shape = RoundedCornerShape(4.dp)
                        clip = true
                    },
                state = swipeToDismissBoxState,
                backgroundContent = {},
                enableDismissFromStartToEnd = false,
                gesturesEnabled = !isDragging,
                onDismiss = { swipeToDismissBoxValue ->
                    if (swipeToDismissBoxValue == SwipeToDismissBoxValue.EndToStart) {
                        onDeleteNotification(notificationConfig)
                    }
                }
            ) {
                ListItem(
                    overlineContent = { Text(notificationConfig.notificationTitle) },
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
                        .clip(shape = listItemShape)
                        .pointerInput(notificationConfig.unixTimeStamp) {
                            detectDragGesturesAfterLongPress(
                                onDragStart = {
                                    isDragging = true
                                },
                                onDrag = { change, dragAmount ->
                                    change.consume()
                                    dragOffset += dragAmount
                                    onNotificationDrag()
                                },
                                onDragEnd = {
                                    val insertionIndex = calculateInsertionIndex(
                                        lazyListState = lazyListState,
                                        draggedUnixTimeStamp = notificationConfig.unixTimeStamp,
                                        dragOffsetY = dragOffset.y,
                                        notificationCount = notificationConfigList.size
                                    )

                                    if (insertionIndex != null) {

                                        onNotificationDragEnd(
                                            notificationConfig,
                                            insertionIndex
                                        )
                                    }
                                    // Offset drift Start
                                    //
                                    // Move Forward to new Place
                                    dragOffset = Offset.Zero
                                    isDragging = false
                                },
                                onDragCancel = {
                                    isDragging = false
                                    dragOffset = Offset.Zero
                                }
                            )
                        }
                )
            }
            if (index != notificationConfigList.lastIndex) { Spacer(modifier = Modifier.height(2.dp)) }
        }
    }
}

/**
 * Converts the dragged item's visual center into an insertion index.
 *
 * LazyListItemInfo reports the original layout position because graphicsLayer only moves the
 * rendered item. Adding [dragOffsetY] gives the center currently shown under the user's finger.
 * The dragged item is excluded before counting so the result is an index in the list after that
 * item has been removed, which is exactly the insertion index required by the weight algorithm.
 *
 *
 */
private fun calculateInsertionIndex(
    lazyListState: LazyListState,
    draggedUnixTimeStamp: Long,
    dragOffsetY: Float,
    notificationCount: Int
): Int? {

    if (notificationCount <= 1) {
        return 0
    }  // If it's only one ListItem, exit judge

    val visibleItemInfoList = lazyListState.layoutInfo.visibleItemsInfo
    val draggedItemInfo = visibleItemInfoList.firstOrNull { lazyListItemInfo ->
        lazyListItemInfo.key == draggedUnixTimeStamp
    } ?: return null  // If it can't find draggedItemInfo, exit judge

    // draggedItemInfo.offset + draggedItemInfo.size / 2f, is center-offest in Column
    // plus dragOffset will be draggedItemCenter-offset
    val draggedItemCenter = (draggedItemInfo.offset + draggedItemInfo.size / 2f) + dragOffsetY

    // Items before the viewport are always before the dragged center until edge auto-scroll is
    // introduced. Visible candidates use their measured centers, so variable item heights work.
    val firstVisibleItemIndex = visibleItemInfoList.firstOrNull()?.index ?: 0
    val visibleItemsBeforeDraggedCenter = visibleItemInfoList
        .asSequence() // Lazy Operation
        .filterNot { lazyListItemInfo ->
            lazyListItemInfo.key == draggedUnixTimeStamp
        }
        .count { lazyListItemInfo ->
            val itemCenter =
                lazyListItemInfo.offset + lazyListItemInfo.size / 2f

            // Count only items whose center is above the dragged item's center.
            itemCenter < draggedItemCenter
        }

    // Convert the visible-item-relative count into a full list index.
    val insertionIndex = firstVisibleItemIndex + visibleItemsBeforeDraggedCenter
    val lastIndexForColumn = notificationCount - 1

    return insertionIndex.coerceIn(0, lastIndexForColumn)
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
            notificationConfigList = listOf(NotificationConfig(), NotificationConfig().copy(unixTimeStamp = 10L)),
            modifier = Modifier
                .weight(
                    weight = 1f,
                    fill = false
                )
        )
    }
}
