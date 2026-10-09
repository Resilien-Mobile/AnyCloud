package com.baidaidai.anycloud.data.clipboard.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class LogEntity(
    @PrimaryKey
    val unixTime: Long,
    val host: String,
    val isMatchedResult: Boolean
)