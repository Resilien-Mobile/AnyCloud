package com.baidaidai.anycloud.data.notification.liveupdate.repository

import com.baidaidai.anycloud.data.database.AnyCloudDataBase
import com.baidaidai.anycloud.data.notification.liveupdate.mapper.toLiveUpdateNotificationEntity
import com.baidaidai.anycloud.data.notification.liveupdate.mapper.toNotificationConfig
import com.baidaidai.anycloud.domain.notification.model.NotificationConfig
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LiveUpdateNotificationRepositoryImpl @Inject constructor(
    private val anyCloudDataBase: AnyCloudDataBase
) {
    private val liveUpdateNotificationDao = anyCloudDataBase.liveUpdateNotificationDao()

    /**
     * CURD
     */

    // Create
    suspend fun addOneLiveUpdateNotification(
        notificationConfig: NotificationConfig
    ) {
        val liveUpdateNotificationEntity = notificationConfig.toLiveUpdateNotificationEntity()

        liveUpdateNotificationDao.addOneLiveUpdateNotificationEntity(
            liveUpdateNotificationEntity
        )
    }

    // Update
    suspend fun updateOneLiveUpdateNotification(
        notificationConfig: NotificationConfig
    ) {
        val liveUpdateNotificationEntity = notificationConfig.toLiveUpdateNotificationEntity()

        liveUpdateNotificationDao.updateOneLiveUpdateNotificationEntity(
            liveUpdateNotificationEntity
        )
    }

    suspend fun updateOneLiveUpdateNotificationTaskFinished(
        unixTimeStamp: Long,
        isTaskFinished: Boolean
    ) {
        liveUpdateNotificationDao.updateOneLiveUpdateNotificationTaskFinished(
            unixTimeStamp = unixTimeStamp,
            isTaskFinished = isTaskFinished
        )
    }

    // Read
    fun getAllLiveUpdateNotification(): Flow<List<NotificationConfig>> {
        val liveUpdateNotificationEntityFlow =
            liveUpdateNotificationDao.getAllLiveUpdateNotificationEntity()
        val notificationConfigFlow =
            liveUpdateNotificationEntityFlow.map { liveUpdateNotificationEntityList ->
                liveUpdateNotificationEntityList.map { liveUpdateNotificationEntity ->
                    liveUpdateNotificationEntity.toNotificationConfig()
                }
            }

        return notificationConfigFlow
    }

    // Delete
    suspend fun deleteOneLiveUpdateNotification(
        notificationConfig: NotificationConfig
    ) {
        val liveUpdateNotificationEntity = notificationConfig.toLiveUpdateNotificationEntity()

        liveUpdateNotificationDao.deleteOneLiveUpdateNotificationEntity(
            liveUpdateNotificationEntity
        )
    }
}
