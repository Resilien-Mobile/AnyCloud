package com.baidaidai.anycloud.data.notification.liveupdate.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface LiveUpdateNotificationDAO {
    /**
     * CURD
     */

    // Create
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addOneLiveUpdateNotificationEntity(
        liveUpdateNotificationEntity: LiveUpdateNotificationEntity
    )

    // Update
    @Update
    suspend fun updateOneLiveUpdateNotificationEntity(
        liveUpdateNotificationEntity: LiveUpdateNotificationEntity
    )

    @Query(
        """
        UPDATE LiveUpdateNotificationEntity
        SET isTaskFinished = :isTaskFinished
        WHERE unixTimeStamp = :unixTimeStamp
        """
    )
    suspend fun updateOneLiveUpdateNotificationTaskFinished(
        unixTimeStamp: Long,
        isTaskFinished: Boolean
    )

    // Read
    @Query("SELECT * FROM LiveUpdateNotificationEntity")
    fun getAllLiveUpdateNotificationEntity(): Flow<List<LiveUpdateNotificationEntity>>

    // Delete
    @Delete
    suspend fun deleteOneLiveUpdateNotificationEntity(
        liveUpdateNotificationEntity: LiveUpdateNotificationEntity
    )
}
