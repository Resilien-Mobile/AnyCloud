package com.baidaidai.anycloud.data.clipboard.database

import androidx.room.Dao
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

    // Read
    @Query("SELECT * FROM PolicyEntity")
    fun observePolicyEntities(): Flow<List<PolicyEntity>>

    @Query("SELECT COUNT(*) FROM PolicyEntity")
    fun observeTotalPolicyCount(): Flow<Int>

    // Delete
}
