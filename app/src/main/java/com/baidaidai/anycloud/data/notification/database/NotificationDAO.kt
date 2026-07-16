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

    // Read
    @Query("SELECT * FROM NotificationEntity")
    fun getAllNotificationEntity(): Flow<List<NotificationEntity>>

    // Delete
    @Delete
    suspend fun deleteOneNotification(notificationEntity: NotificationEntity)
}