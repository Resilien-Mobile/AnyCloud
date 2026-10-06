package com.baidaidai.anycloud.domain.clipboard

data class PolicyStatus(
    val unixTimeStamp: Long,
    val isEnabled: Boolean,
    val isBuiltIn: Boolean
)
