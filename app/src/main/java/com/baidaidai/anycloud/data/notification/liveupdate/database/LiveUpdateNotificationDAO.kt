package com.baidaidai.anycloud.data.notification.liveupdate.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
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

    @Transaction
    suspend fun addOneLiveUpdateNotificationEntityWithWeight(
        entity: LiveUpdateNotificationEntity
    ) {
        addOneLiveUpdateNotificationEntity(entity)

        // Keep newly appended notifications far enough apart for later midpoint insertion.
        val nextWeight = (getMaxNotificationWeight() ?: 0L) + 10000L

        addOneLiveUpdateNotificationPositionEntity(
            LiveUpdateNotificationPositionEntity(
                unixTimeStamp = entity.unixTimeStamp,
                notificationWeight = nextWeight
            )
        )
    }

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addOneLiveUpdateNotificationPositionEntity(
        liveUpdateNotificationPositionEntity: LiveUpdateNotificationPositionEntity
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

    @Query(
        """
        UPDATE LiveUpdateNotificationPositionEntity
        SET notificationWeight = :notificationWeight
        WHERE unixTimeStamp = :unixTimeStamp
        """
    )
    suspend fun updateNotificationWeightByUnixTimeStamp(
        unixTimeStamp: Long,
        notificationWeight: Long
    )

    // Read
    @Query(
        """
        SELECT notification.*
        FROM LiveUpdateNotificationEntity AS notification
        INNER JOIN LiveUpdateNotificationPositionEntity AS position
            ON notification.unixTimeStamp = position.unixTimeStamp
        ORDER BY position.notificationWeight ASC, notification.unixTimeStamp ASC
        """
    )
    fun getAllLiveUpdateNotificationEntity(): Flow<List<LiveUpdateNotificationEntity>>

    @Query("SELECT MAX(notificationWeight) FROM LiveUpdateNotificationPositionEntity")
    suspend fun getMaxNotificationWeight(): Long?

    @Query(
        """
        SELECT notificationWeight
        FROM LiveUpdateNotificationPositionEntity
        WHERE unixTimeStamp != :unixTimeStamp
        ORDER BY notificationWeight ASC, unixTimeStamp ASC
        """
    )
    suspend fun getAllNotificationWeightExceptUnixTimeStamp(
        unixTimeStamp: Long
    ): List<Long>

    // Delete
    @Delete
    suspend fun deleteOneLiveUpdateNotificationEntity(
        liveUpdateNotificationEntity: LiveUpdateNotificationEntity
    )
}
