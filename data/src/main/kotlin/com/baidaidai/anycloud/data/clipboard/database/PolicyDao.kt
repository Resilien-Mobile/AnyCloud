package com.baidaidai.anycloud.data.clipboard.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PolicyDao {
    /**
     * CRUD
     */

    // Create
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertPolicyEntity(
        policyEntity: PolicyEntity
    )

    // Update
    @Query("UPDATE PolicyEntity SET targetPackageName = :targetPackageName WHERE policyGroup = :policyGroup")
    suspend fun updatePolicyTargetPackageName(
        policyGroup: Long,
        targetPackageName: String
    )

    // Read
    @Query("SELECT EXISTS(SELECT 1 FROM PolicyEntity WHERE policyGroup = :policyGroup AND policyType = :policyType AND policyContent = :policyContent)")
    suspend fun hasPolicyEntity(
        policyGroup: Long,
        policyType: String,
        policyContent: String
    ): Boolean

    @Query("SELECT MAX(unixTimeStamp) FROM PolicyEntity")
    suspend fun getMaxPolicyUnixTimeStamp(): Long?

    @Query("SELECT * FROM PolicyEntity")
    fun observePolicyEntities(): Flow<List<PolicyEntity>>

    @Query("SELECT COUNT(*) FROM PolicyEntity")
    fun observeTotalPolicyCount(): Flow<Int>

    // Delete
    @Delete
    suspend fun deletePolicyEntity(
        policyEntity: PolicyEntity
    )
}
