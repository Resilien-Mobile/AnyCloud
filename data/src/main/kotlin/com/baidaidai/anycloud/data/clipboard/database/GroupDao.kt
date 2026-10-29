package com.baidaidai.anycloud.data.clipboard.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface GroupDao {
    /**
     * CRUD
     */

    // Create
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertGroupEntity(
        groupEntity: GroupEntity
    )

    // Update
    @Update
    suspend fun updateGroupEntity(
        groupEntity: GroupEntity
    )

    // Read
    @Query("SELECT * FROM GroupEntity WHERE groupName = :groupName ORDER BY unixTimeStamp ASC")
    suspend fun listGroupEntitiesByName(
        groupName: String
    ): List<GroupEntity>

    @Query("SELECT MAX(unixTimeStamp) FROM GroupEntity")
    suspend fun getMaxGroupUnixTimeStamp(): Long?

    @Query("SELECT * FROM GroupEntity")
    fun observeGroupEntities(): Flow<List<GroupEntity>>

    // Delete
    @Delete
    suspend fun deleteGroupEntity(
        groupEntity: GroupEntity
    )
}
