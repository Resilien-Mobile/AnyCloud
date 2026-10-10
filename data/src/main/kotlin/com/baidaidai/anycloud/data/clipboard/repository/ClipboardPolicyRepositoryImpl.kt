package com.baidaidai.anycloud.data.clipboard.repository

import com.baidaidai.anycloud.data.clipboard.mapper.ClipboardPolicyMapper.toClipboardPolicy
import com.baidaidai.anycloud.data.clipboard.mapper.ClipboardPolicyMapper.toPolicyEntity
import com.baidaidai.anycloud.data.database.AnyCloudDataBase
import com.baidaidai.anycloud.domain.clipboard.ClipboardPolicy
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ClipboardPolicyRepositoryImpl @Inject constructor(
    private val anyCloudDataBase: AnyCloudDataBase
) {
    private val policyDao = anyCloudDataBase.policyDao()

    /**
     * CRUD
     */

    // Create
    suspend fun createClipboardPolicy(
        clipboardPolicy: ClipboardPolicy
    ) {
        val policyEntity = clipboardPolicy.toPolicyEntity()

        policyDao.insertPolicyEntity(policyEntity)
    }

    // Update

    // Read
    fun observeClipboardPolicies(): Flow<List<ClipboardPolicy>> {

        val policyEntityFlow = policyDao.observePolicyEntities()

        val clipboardPolicyFlow = policyEntityFlow.map { policyEntityList ->
            policyEntityList.map { policyEntity ->
                policyEntity.toClipboardPolicy()
            }
        }

        return clipboardPolicyFlow
    }

    fun observeTotalPolicyCount(): Flow<Int> {
        val totalPolicyCountFlow = policyDao.observeTotalPolicyCount()

        return totalPolicyCountFlow
    }

    // Delete
    suspend fun deleteClipboardPolicy(
        clipboardPolicy: ClipboardPolicy
    ) {
        val policyEntity = clipboardPolicy.toPolicyEntity()

        policyDao.deletePolicyEntity(policyEntity)
    }
}
