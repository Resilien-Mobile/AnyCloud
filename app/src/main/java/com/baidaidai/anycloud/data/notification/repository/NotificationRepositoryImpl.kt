package com.baidaidai.anycloud.data.notification.repository

import com.baidaidai.anycloud.data.database.AnyCloudDataBase
import com.baidaidai.anycloud.data.notification.mapper.toNotificationConfig
import com.baidaidai.anycloud.data.notification.mapper.toNotificationEntity
import com.baidaidai.anycloud.domain.notification.model.NotificationConfig
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class NotificationRepositoryImpl @Inject constructor(
    private val anyCloudDataBase: AnyCloudDataBase
) {
    private val notificationDao = anyCloudDataBase.notificationDao()

    /**
     * CURD
     */

    // Create
    suspend fun addOneNotification(notificationConfig: NotificationConfig) {
        val notificationEntity = notificationConfig.toNotificationEntity()

        notificationDao.addOneNotificationEntity(notificationEntity)
    }

    // Update
    suspend fun updateOneNotification(notificationConfig: NotificationConfig) {
        val notificationEntity = notificationConfig.toNotificationEntity()

        notificationDao.updateOneNotificationEntity(notificationEntity)
    }

    suspend fun updateOneNotificationTaskFinished(
        unixTimeStamp: Long,
        isTaskFinished: Boolean
    ) {
        notificationDao.updateOneNotificationTaskFinished(
            unixTimeStamp = unixTimeStamp,
            isTaskFinished = isTaskFinished
        )
    }

    // Read
    fun getAllNotification(): Flow<List<NotificationConfig>> {
        val notificationEntityFlow = notificationDao.getAllNotificationEntity()
        val notificationConfigFlow = notificationEntityFlow.map { notificationEntityList ->
            notificationEntityList.map { notificationEntity ->
                notificationEntity.toNotificationConfig()
            }
        }

        return notificationConfigFlow
    }

    suspend fun getMaxNotificationIDBetween(
        minNotificationID: Int,
        maxNotificationID: Int
    ): Int? {
        val maxNotificationIDInDataBase = notificationDao.getMaxNotificationIDBetween(
            minNotificationID = minNotificationID,
            maxNotificationID = maxNotificationID
        )

        return maxNotificationIDInDataBase
    }

    // Delete
    suspend fun deleteOneNotification(notificationConfig: NotificationConfig) {
        val notificationEntity = notificationConfig.toNotificationEntity()

        notificationDao.deleteOneNotification(notificationEntity)
    }
}
