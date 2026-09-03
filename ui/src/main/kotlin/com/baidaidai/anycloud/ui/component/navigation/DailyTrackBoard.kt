package com.baidaidai.anycloud.ui.component.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.baidaidai.anycloud.ui.theme.AnyCloudTheme

/*
实际没有 365/366 天，而是模拟一年时间，只有 364 天，只消费 364 天
只有 91 个格子，利用权重，点亮对应的 “集合区域”
w = 13, h = 7, weight = 4 cell/day
*/
@Composable
fun DailyTrackBoard(
    modifier: Modifier = Modifier,
    dailyEffortList: List<Int> = List(366) { 0 },
    contentPadding: PaddingValues = PaddingValues(16.dp)
){
    require(dailyEffortList.size == 366) {
        "dailyEffortList must contain exactly 366 values"
    }

    val dailyTrackCellStateList = remember(dailyEffortList) { createDailyTrackCellStateList(dailyEffortList) }

    BoxWithConstraints(
        modifier = modifier
            // TrackBoard 的四边留白只由这一层负责。
            .padding(contentPadding)
    ) {
        // 固定 Spacer 先占据宽度，剩余空间再平均分给 13 列 Cell。
        val totalHorizontalSpacerWidth = 4.dp * (13 - 1)
        val availableCellWidth = (maxWidth - totalHorizontalSpacerWidth).coerceAtLeast(0.dp)
        val cellWidth = availableCellWidth / 13


        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            // x 轴每前进一步，就从 y = 0 开始向下填满一整列。
            val dailyTrackCellStateIterator = dailyTrackCellStateList.iterator()

            repeat(13) { x ->
                Column {
                    repeat(7) { y ->

                        // 一个 “虚拟指针”，追踪格子 Meta 数据
                        val dailyTrackCellState = dailyTrackCellStateIterator.next()

                        DailyTrackCell(
                            color = calculateDailyTrackCellColor(
                                dailyTrackCellState.colorDepth
                            ),
                            modifier = Modifier.width(cellWidth)
                        )

                        // 格与格 Gap
                        if (y != 6) {
                            Spacer(modifier = Modifier.height(4.dp))
                        }
                    }
                }

                // 列与列 Gap
                if (x != 12) {
                    Spacer(modifier = Modifier.width(4.dp))
                }
            }
        }
    }
}

@Composable
private fun DailyTrackCell(
    color: Color,
    modifier: Modifier = Modifier
){
    Box(
        modifier = modifier
            .aspectRatio(1f)
            .clip(RoundedCornerShape(2.dp))
            .background(color)
    )
}

@Composable
private fun calculateDailyTrackCellColor(
    colorDepth: DailyTrackCellColorDepth
): Color {
    return lerp(
        start = MaterialTheme.colorScheme.surfaceContainer,
        stop = MaterialTheme.colorScheme.primary,
        fraction = colorDepth.fraction
    )
}

private fun createDailyTrackCellStateList(
    dailyEffortList: List<Int>
): List<DailyTrackCellState> {
    val effortWeightList = mutableListOf<DailyTrackCellState>()
    var currentCellDayCount = 0
    var currentCellEffortWeight = 0L

    // 366 个输入值保持统一契约；当前 13 × 7 面板只消费前 364 天。
    dailyEffortList
        .take(364)
        .forEach { dailyEffort ->
            currentCellDayCount += 1
            currentCellEffortWeight += dailyEffort.coerceAtLeast(0).toLong()

            // 每累计四天就释放一次状态，并清空本轮累计值。
            // 这里计算的是：4天内，带有 颜色深度分级 和总 “努力值 ”的一个 DataClass
            if (currentCellDayCount == 4) {
                effortWeightList.add(
                    DailyTrackCellState(
                        effortWeight = currentCellEffortWeight,
                        colorDepth = calculateColorDepth(currentCellEffortWeight)
                    )
                )
                currentCellDayCount = 0
                currentCellEffortWeight = 0L
            }
        }

    return effortWeightList

}

private fun calculateColorDepth(
    effortWeight: Long
): DailyTrackCellColorDepth {

    // 取 颜色深度分级 保底
    return DailyTrackCellColorDepth.entries
        .last { colorDepth ->
            effortWeight >= colorDepth.promotionScore
        }
}

private data class DailyTrackCellState(
    val effortWeight: Long,
    val colorDepth: DailyTrackCellColorDepth
)

private enum class DailyTrackCellColorDepth(
    val promotionScore: Long,
    val fraction: Float
) {
    Empty(promotionScore = 0L, fraction = 0f),
    Low(promotionScore = 4L, fraction = 0.25f),
    Medium(promotionScore = 8L, fraction = 0.50f),
    High(promotionScore = 12L, fraction = 0.75f),
    Full(promotionScore = 16L, fraction = 1f)
}


@PreviewLightDark
@Composable
private fun DailyTrackBoardPreview() {
    val previewEffortList = List(366) { dayIndex ->
        when {
            dayIndex % 19 == 0 -> 12
            dayIndex % 7 == 0 -> 7
            dayIndex % 3 == 0 -> 3
            else -> 0
        }
    }

    AnyCloudTheme(dynamicColor = false) {
        Surface {
            DailyTrackBoard(
                dailyEffortList = previewEffortList,
                modifier = Modifier.width(360.dp)
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun DailyTrackCellPreview() {
    AnyCloudTheme(dynamicColor = false) {
        Surface {
            DailyTrackCell(
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .padding(16.dp)
                    .size(48.dp)
            )
        }
    }
}
