package com.baidaidai.anycloud.data.dailytrack.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface DailyTrackDao {
    /**
     * CRUD
    */

    // Create
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertDailyTrack(
        dailyTrackEntity: DailyTrackEntity
    )

    // Update
    @Query(
        """
        UPDATE DailyTrackEntity
        SET dateScore = dateScore + 1
        WHERE dateIndex = :dateIndex
        """
    )
    suspend fun updateDailyTrackScore(
        dateIndex: Int
    )

    // Read
    @Query(
        """
        SELECT *
        FROM DailyTrackEntity
        WHERE dateIndex = :dateIndex
        LIMIT 1
        """
    )
    suspend fun findDailyTrackByDateIndex(
        dateIndex: Int
    ): DailyTrackEntity?

    @Query(
        """
        SELECT *
        FROM DailyTrackEntity
        ORDER BY dateIndex ASC
        """
    )
    fun observeDailyTracks(): Flow<List<DailyTrackEntity>>

    // Delete
    @Query("DELETE FROM DailyTrackEntity")
    suspend fun deleteAllDailyTracks()
}
