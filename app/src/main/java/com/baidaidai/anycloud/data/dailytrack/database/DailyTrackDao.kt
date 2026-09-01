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

    // Read
    @Query(
        """
        SELECT *
        FROM DailyTrackEntity
        ORDER BY dateIndex ASC, dateUnixStamp ASC
        """
    )
    fun observeDailyTracks(): Flow<List<DailyTrackEntity>>

    // Delete
    @Query("DELETE FROM DailyTrackEntity")
    suspend fun deleteAllDailyTracks()
}
