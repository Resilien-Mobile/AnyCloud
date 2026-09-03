package com.baidaidai.anycloud.domain.notification.model

data class LiveUpdateProgressConfig(
    val progressGapList: List<Int>,
    val progressPointList: List<Int>,
    val progressCursor: Int
)
