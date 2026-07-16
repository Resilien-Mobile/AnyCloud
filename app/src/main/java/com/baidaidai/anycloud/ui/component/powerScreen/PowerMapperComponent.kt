package com.baidaidai.anycloud.ui.component.powerScreen


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PowerMapperComponent(
    modifier: Modifier = Modifier
){
    val lineColor = MaterialTheme.colorScheme.onPrimaryContainer
    val gridColor = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.18f)
    val labelColor = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.72f)

    val scores = listOf(
        53f, 55f, 71f,
        89f, 65f, 71f,
        90f, 65f, 73f,
        66f, 66f, 89f
    )

    val minScore = 0f
    val maxScore = 100f
    val scoreRange = maxScore - minScore
    val yLabels = listOf(99, 75, 50, 25, 0)

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Layout(
            modifier = Modifier
                .width(36.dp)
                .fillMaxHeight(),
            content = {
                yLabels.forEach { label ->
                    Text(
                        text = label.toString(),
                        color = labelColor,
                        fontSize = 10.sp,
                        textAlign = TextAlign.End
                    )
                }
            }
        ) { measurables, constraints ->
            val placeables = measurables.map { measurable ->
                measurable.measure(constraints.copy(minWidth = 0, minHeight = 0))
            }

            layout(constraints.maxWidth, constraints.maxHeight) {
                placeables.forEachIndexed { index, placeable ->
                    val ratio = if (placeables.size > 1) {
                        index / (placeables.size - 1).toFloat()
                    } else {
                        0f
                    }

                    val x = constraints.maxWidth - placeable.width
                    val y = (constraints.maxHeight * ratio - placeable.height/2).toInt()

                    placeable
                        .placeRelative(x, y)
                }
            }
        }
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxSize()
                .drawWithCache {
                    val chartWidth = size.width
                    val chartHeight = size.height
                    val xStep = if (scores.size > 1) chartWidth / (scores.size - 1) else 0f

                    val points = scores.mapIndexed { index, score ->
                        val x = index * xStep
                        val normalized = (score - minScore) / scoreRange
                        val y = chartHeight - normalized * chartHeight
                        Offset(x, y)
                    }

                    val path = Path().apply {
                        points.firstOrNull()?.let { firstPoint ->
                            moveTo(firstPoint.x, firstPoint.y)

                            for (i in 1 until points.size) {
                                val previous = points[i - 1]
                                val current = points[i]
                                val controlX = (previous.x + current.x) / 2f

                                cubicTo(
                                    controlX, previous.y,
                                    controlX, current.y,
                                    current.x, current.y
                                )
                            }
                        }
                    }

                    onDrawBehind {
                        yLabels.forEach { label ->
                            var normalized = (label - minScore) / scoreRange
                            val y = chartHeight - normalized * chartHeight

                            drawLine(
                                color = gridColor,
                                start = Offset(0f, y),
                                end = Offset(chartWidth, y)
                            )
                        }

                        drawPath(
                            path = path,
                            color = lineColor,
                            style = Stroke(
                                width = 4.dp.toPx(),
                                cap = StrokeCap.Round,
                                join = StrokeJoin.Round
                            )
                        )
                    }
                }
        )
    }
}

@PreviewLightDark
@Composable
private fun _preview_(){
    PowerMapperComponent(
        modifier = Modifier
            .size(200.dp)
            .background(MaterialTheme.colorScheme.primaryContainer)
            .padding(16.dp)
    )
}