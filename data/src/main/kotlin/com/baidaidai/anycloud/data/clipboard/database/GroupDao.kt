package com.baidaidai.anycloud.data.clipboard.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
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

    // Read
    @Query("SELECT * FROM GroupEntity")
    fun observeGroupEntities(): Flow<List<GroupEntity>>

    // Delete
    @Delete
    suspend fun deleteGroupEntity(
        groupEntity: GroupEntity
    )
}
