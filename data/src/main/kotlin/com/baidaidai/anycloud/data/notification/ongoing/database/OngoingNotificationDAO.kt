package com.baidaidai.anycloud.data.notification.ongoing.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface OngoingNotificationDAO {
    /**
     * CURD
     */

    // Create
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addOneOngoingNotificationEntity(
        ongoingNotificationEntity: OngoingNotificationEntity
    )

    // Update
    @Update
    suspend fun updateOneOngoingNotificationEntity(
        ongoingNotificationEntity: OngoingNotificationEntity
    )

    @Query(
        """
        UPDATE OngoingNotificationEntity
        SET isTaskFinished = :isTaskFinished
        WHERE unixTimeStamp = :unixTimeStamp
        """
    )
    suspend fun updateOneOngoingNotificationTaskFinished(
        unixTimeStamp: Long,
        isTaskFinished: Boolean
    )

    // Read
    @Query("SELECT * FROM OngoingNotificationEntity")
    fun getAllOngoingNotificationEntity(): Flow<List<OngoingNotificationEntity>>

    @Query(
        "SELECT MAX(notificationID) FROM OngoingNotificationEntity " +
                "WHERE notificationID BETWEEN :minNotificationID AND :maxNotificationID"
    )
    suspend fun getMaxOngoingNotificationIDBetween(
        minNotificationID: Int,
        maxNotificationID: Int
    ): Int?

    // Delete
    @Delete
    suspend fun deleteOneOngoingNotificationEntity(
        ongoingNotificationEntity: OngoingNotificationEntity
    )
}
