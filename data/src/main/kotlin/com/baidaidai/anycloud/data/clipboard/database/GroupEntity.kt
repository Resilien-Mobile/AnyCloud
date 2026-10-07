package com.baidaidai.anycloud.data.clipboard.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class GroupEntity(
    @PrimaryKey
    val unixTimeStamp: Long,
    val groupName: String,
    val targetPackageName: String
)
