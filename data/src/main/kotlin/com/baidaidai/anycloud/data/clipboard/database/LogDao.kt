package com.baidaidai.anycloud.data.clipboard.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface LogDao {
    /**
     * CRUD
     */

    // Create
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertLogEntity(
        logEntity: LogEntity
    )

    // Update

    // Read
    @Query("SELECT * FROM LogEntity ORDER BY unixTime DESC")
    fun observeLogEntities(): Flow<List<LogEntity>>

    @Query("SELECT COUNT(*) FROM LogEntity")
    suspend fun getLogEntityCount(): Int

    // Delete
    @Query(
        """
            DELETE FROM LogEntity
            WHERE unixTime = (
                SELECT unixTime
                FROM LogEntity
                ORDER BY unixTime ASC
                LIMIT 1
            )
        """
    )
    suspend fun deleteLastLogEntity()
}