package com.baidaidai.anycloud.domain.clipboard

data class ClipboardGroup(
    val unixTimeStamp: Long,
    val groupName: String,
    val targetPackageName: String
)