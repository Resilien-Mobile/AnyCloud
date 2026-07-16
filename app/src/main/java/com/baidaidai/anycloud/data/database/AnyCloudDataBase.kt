package com.baidaidai.anycloud.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.baidaidai.anycloud.data.notification.database.NotificationDAO
import com.baidaidai.anycloud.data.notification.database.NotificationEntity

@Database(
    entities = [
        NotificationEntity::class

        // 其他表
    ],
    version = 1
)
abstract class AnyCloudDataBase: RoomDatabase() {
    abstract fun notificationDao(): NotificationDAO

    //其他DAO
}