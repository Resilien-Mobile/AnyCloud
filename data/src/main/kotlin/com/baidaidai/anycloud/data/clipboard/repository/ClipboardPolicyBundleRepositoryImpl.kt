package com.baidaidai.anycloud.data.clipboard.repository

import androidx.room.withTransaction
import com.baidaidai.anycloud.core.array.formatAsMultilineString
import com.baidaidai.anycloud.data.clipboard.database.GroupEntity
import com.baidaidai.anycloud.data.clipboard.database.PolicyEntity
import com.baidaidai.anycloud.data.database.AnyCloudDataBase
import com.baidaidai.anycloud.domain.clipboard.ClipboardError
import com.baidaidai.anycloud.domain.clipboard.ClipboardPolicyBundle
import com.github.michaelbull.result.Result
import com.github.michaelbull.result.mapError
import com.github.michaelbull.result.runCatching
import javax.inject.Inject

class ClipboardPolicyBundleRepositoryImpl @Inject constructor(
    private val anyCloudDataBase: AnyCloudDataBase
) {
    private val groupDao = anyCloudDataBase.groupDao()
    private val policyDao = anyCloudDataBase.policyDao()

    /**
     * CRUD
     */

    // Create
    suspend fun addClipboardPolicyBundle(
        clipboardPolicyBundle: ClipboardPolicyBundle
    ): Result<Unit, ClipboardError> = runCatching {
        anyCloudDataBase.withTransaction {
            val currentUnixTimeStamp = System.currentTimeMillis()
            val maxGroupUnixTimeStamp = groupDao.getMaxGroupUnixTimeStamp() ?: 0L
            val maxPolicyUnixTimeStamp = policyDao.getMaxPolicyUnixTimeStamp() ?: 0L
            var nextGroupUnixTimeStamp = maxOf(
                currentUnixTimeStamp,
                maxGroupUnixTimeStamp + 1L
            )
            var nextPolicyUnixTimeStamp = maxOf(
                currentUnixTimeStamp,
                maxPolicyUnixTimeStamp + 1L
            )

            val groupUnixTimeStampByName = mutableMapOf<String, Long>()
            val targetPackageNameByGroupName = clipboardPolicyBundle.groups.associate { group ->
                group.groupName to group.targetPackageName
            }

            clipboardPolicyBundle.groups.forEach { group ->
                val existingGroupEntity = groupDao
                    .listGroupEntitiesByName(group.groupName)
                    .firstOrNull()

                val resolvedGroupEntity = if (existingGroupEntity == null) {
                    val newGroupEntity = GroupEntity(
                        unixTimeStamp = nextGroupUnixTimeStamp,
                        groupName = group.groupName,
                        targetPackageName = group.targetPackageName
                    )
                    nextGroupUnixTimeStamp += 1L

                    groupDao.insertGroupEntity(newGroupEntity)

                    newGroupEntity
                } else {
                    val updatedGroupEntity = existingGroupEntity.copy(
                        targetPackageName = group.targetPackageName
                    )

                    groupDao.updateGroupEntity(updatedGroupEntity)

                    updatedGroupEntity
                }

                groupUnixTimeStampByName[group.groupName] = resolvedGroupEntity.unixTimeStamp
                policyDao.updatePolicyTargetPackageName(
                    policyGroup = resolvedGroupEntity.unixTimeStamp,
                    targetPackageName = group.targetPackageName
                )
            }

            clipboardPolicyBundle.policies.forEach { policy ->
                val policyGroupUnixTimeStamp = requireNotNull(
                    groupUnixTimeStampByName[policy.groupName]
                ) {
                    "Policy group does not exist: ${policy.groupName}"
                }
                val policyType = policy.policyType.name
                val hasPolicyEntity = policyDao.hasPolicyEntity(
                    policyGroup = policyGroupUnixTimeStamp,
                    policyType = policyType,
                    policyContent = policy.policyContent
                )

                if (!hasPolicyEntity) {
                    val newPolicyEntity = PolicyEntity(
                        unixTimeStamp = nextPolicyUnixTimeStamp,
                        policyType = policyType,
                        policyContent = policy.policyContent,
                        policyGroup = policyGroupUnixTimeStamp,
                        targetPackageName = targetPackageNameByGroupName.getValue(policy.groupName)
                    )
                    nextPolicyUnixTimeStamp += 1L

                    policyDao.insertPolicyEntity(newPolicyEntity)
                }
            }
        }
    }.mapError { throwable ->

        ClipboardError(
            errorMessage = "Policy import failed",
            errorCause = throwable.stackTrace.formatAsMultilineString(),
            errorCompanion = when (throwable) {
                is IllegalArgumentException -> throwable.message ?: "A policy references an unknown group."
                else -> "The local database could not save the imported policy groups and rules."
            }
        )

    }
}
