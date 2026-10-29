package com.baidaidai.anycloud.domain.clipboard

data class ClipboardPolicyBundle(
    val groups: List<ClipboardPolicyBundleGroup>,
    val policies: List<ClipboardPolicyBundleRule>
)