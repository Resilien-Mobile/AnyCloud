package com.baidaidai.anycloud.domain.clipboard

data class ClipboardLog(
    val unixTime: Long,
    val host: String,
    val isMatchedResult: Boolean
)