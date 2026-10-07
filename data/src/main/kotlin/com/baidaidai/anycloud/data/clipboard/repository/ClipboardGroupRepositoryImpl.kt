package com.baidaidai.anycloud.data.clipboard.repository

import com.baidaidai.anycloud.data.clipboard.mapper.ClipboardGroupMapper.toClipboardGroup
import com.baidaidai.anycloud.data.clipboard.mapper.ClipboardGroupMapper.toGroupEntity
import com.baidaidai.anycloud.data.database.AnyCloudDataBase
import com.baidaidai.anycloud.domain.clipboard.ClipboardGroup
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ClipboardGroupRepositoryImpl @Inject constructor(
    private val anyCloudDataBase: AnyCloudDataBase
) {
    private val groupDao = anyCloudDataBase.groupDao()

    /**
     * CRUD
     */

    // Create
    suspend fun createClipboardGroup(
        clipboardGroup: ClipboardGroup
    ) {
        val groupEntity = clipboardGroup.toGroupEntity()

        groupDao.insertGroupEntity(groupEntity)
    }

    // Update

    // Read
    fun observeClipboardGroups(): Flow<List<ClipboardGroup>> {

        val groupEntityFlow = groupDao.observeGroupEntities()

        val clipboardGroupFlow = groupEntityFlow.map { groupEntityList ->
            groupEntityList.map { groupEntity ->
                groupEntity.toClipboardGroup()
            }
        }

        return clipboardGroupFlow
    }

    // Delete
}
