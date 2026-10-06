package com.baidaidai.anycloud.data.clipboard.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PolicyEntity(
    @PrimaryKey
    val unixTimeStamp: Long,
    val policyType: String,
    val policyContent: String,
    val policyGroup: String?
)
