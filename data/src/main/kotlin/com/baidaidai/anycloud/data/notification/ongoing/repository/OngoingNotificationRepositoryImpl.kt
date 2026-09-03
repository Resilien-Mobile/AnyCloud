package com.baidaidai.anycloud.data.notification.ongoing.repository

import com.baidaidai.anycloud.data.database.AnyCloudDataBase
import com.baidaidai.anycloud.data.notification.ongoing.mapper.toNotificationConfig
import com.baidaidai.anycloud.data.notification.ongoing.mapper.toOngoingNotificationEntity
import com.baidaidai.anycloud.domain.notification.model.NotificationConfig
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class OngoingNotificationRepositoryImpl @Inject constructor(
    private val anyCloudDataBase: AnyCloudDataBase
) {
    private val ongoingNotificationDao = anyCloudDataBase.ongoingNotificationDao()

    /**
     * CURD
     */

    // Create
    suspend fun addOneOngoingNotification(notificationConfig: NotificationConfig) {
        val ongoingNotificationEntity = notificationConfig.toOngoingNotificationEntity()

        ongoingNotificationDao.addOneOngoingNotificationEntity(ongoingNotificationEntity)
    }

    // Update
    suspend fun updateOneOngoingNotification(notificationConfig: NotificationConfig) {
        val ongoingNotificationEntity = notificationConfig.toOngoingNotificationEntity()

        ongoingNotificationDao.updateOneOngoingNotificationEntity(ongoingNotificationEntity)
    }

    suspend fun updateOneOngoingNotificationTaskFinished(
        unixTimeStamp: Long,
        isTaskFinished: Boolean
    ) {
        ongoingNotificationDao.updateOneOngoingNotificationTaskFinished(
            unixTimeStamp = unixTimeStamp,
            isTaskFinished = isTaskFinished
        )
    }

    // Read
    fun getAllOngoingNotification(): Flow<List<NotificationConfig>> {
        val ongoingNotificationEntityFlow =
            ongoingNotificationDao.getAllOngoingNotificationEntity()
        val notificationConfigFlow =
            ongoingNotificationEntityFlow.map { ongoingNotificationEntityList ->
                ongoingNotificationEntityList.map { ongoingNotificationEntity ->
                    ongoingNotificationEntity.toNotificationConfig()
                }
            }

        return notificationConfigFlow
    }

    suspend fun getMaxOngoingNotificationIDBetween(
        minNotificationID: Int,
        maxNotificationID: Int
    ): Int? {
        val maxNotificationIDInDataBase =
            ongoingNotificationDao.getMaxOngoingNotificationIDBetween(
                minNotificationID = minNotificationID,
                maxNotificationID = maxNotificationID
            )

        return maxNotificationIDInDataBase
    }

    // Delete
    suspend fun deleteOneOngoingNotification(notificationConfig: NotificationConfig) {
        val ongoingNotificationEntity = notificationConfig.toOngoingNotificationEntity()

        ongoingNotificationDao.deleteOneOngoingNotificationEntity(ongoingNotificationEntity)
    }
}
