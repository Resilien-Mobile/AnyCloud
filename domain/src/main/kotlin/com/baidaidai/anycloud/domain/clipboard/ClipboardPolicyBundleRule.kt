package com.baidaidai.anycloud.domain.clipboard

data class ClipboardPolicyBundleRule(
    val policyType: PolicyType,
    val policyContent: String,
    val groupName: String
)