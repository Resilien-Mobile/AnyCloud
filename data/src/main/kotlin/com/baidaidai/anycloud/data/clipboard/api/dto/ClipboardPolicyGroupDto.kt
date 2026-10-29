package com.baidaidai.anycloud.data.clipboard.api.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ClipboardPolicyGroupDto(
    val name: String,
    @SerialName("package")
    val targetPackageName: String
)