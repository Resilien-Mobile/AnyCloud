package com.baidaidai.anycloud.data.clipboard.api.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ClipboardPolicyBundleDto(
    @SerialName("policy-groups")
    val policyGroups: List<ClipboardPolicyGroupDto> = emptyList(),
    val policies: List<String> = emptyList()
)