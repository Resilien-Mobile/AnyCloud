package com.baidaidai.anycloud.data.clipboard.mapper

import com.baidaidai.anycloud.data.clipboard.database.GroupEntity
import com.baidaidai.anycloud.domain.clipboard.ClipboardGroup

object ClipboardGroupMapper {

    internal fun GroupEntity.toClipboardGroup(): ClipboardGroup {
        val clipboardGroup = ClipboardGroup(
            unixTimeStamp = unixTimeStamp,
            groupName = groupName,
            targetPackageName = targetPackageName
        )

        return clipboardGroup
    }

    internal fun ClipboardGroup.toGroupEntity(): GroupEntity {
        val groupEntity = GroupEntity(
            unixTimeStamp = unixTimeStamp,
            groupName = groupName,
            targetPackageName = targetPackageName
        )

        return groupEntity
    }
}
