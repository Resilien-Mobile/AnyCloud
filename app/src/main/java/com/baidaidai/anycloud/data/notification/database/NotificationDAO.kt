package com.baidaidai.anycloud.data.notification.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface NotificationDAO {
    /**
     * CURD
     */

    // Create
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addOneNotificationEntity(notificationEntity: NotificationEntity)

    // Update
    @Update
    suspend fun updateOneNotificationEntity(notificationEntity: NotificationEntity)

    @Query(
        """
        UPDATE NotificationEntity
        SET isTaskFinished = :isTaskFinished
        WHERE unixTimeStamp = :unixTimeStamp
        """
    )
    suspend fun updateOneNotificationTaskFinished(
        unixTimeStamp: Long,
        isTaskFinished: Boolean
    )

    // Read
    @Query("SELECT * FROM NotificationEntity")
    fun getAllNotificationEntity(): Flow<List<NotificationEntity>>

    @Query(
        "SELECT MAX(notificationID) FROM NotificationEntity " +
                "WHERE notificationID BETWEEN :minNotificationID AND :maxNotificationID"
    )
    suspend fun getMaxNotificationIDBetween(
        minNotificationID: Int,
        maxNotificationID: Int
    ): Int?

    // Delete
    @Delete
    suspend fun deleteOneNotification(notificationEntity: NotificationEntity)
}
