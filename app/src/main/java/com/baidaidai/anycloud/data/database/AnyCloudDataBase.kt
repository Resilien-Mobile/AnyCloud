package com.baidaidai.anycloud.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.baidaidai.anycloud.data.notification.ongoing.database.OngoingNotificationDAO
import com.baidaidai.anycloud.data.notification.ongoing.database.OngoingNotificationEntity

@Database(
    entities = [
        OngoingNotificationEntity::class

        // 其他表
    ],
    version = 1
)
abstract class AnyCloudDataBase: RoomDatabase() {
    abstract fun ongoingNotificationDao(): OngoingNotificationDAO

    //其他DAO
}