package com.baidaidai.anycloud.domain.clipboard

data class ClipboardPolicy(
    val unixTimeStamp: Long,
    val policyType: PolicyType = PolicyType.DOMAIN,
    val policyContent: String,
    val policyGroup: String?
)
