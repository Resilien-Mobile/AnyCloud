package com.baidaidai.anycloud.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.baidaidai.anycloud.data.dailycount.database.DailyCountDao
import com.baidaidai.anycloud.data.dailycount.database.DailyCountEntity
import com.baidaidai.anycloud.data.dailytrack.database.DailyTrackDao
import com.baidaidai.anycloud.data.dailytrack.database.DailyTrackEntity
import com.baidaidai.anycloud.data.notification.liveupdate.database.LiveUpdateNotificationDAO
import com.baidaidai.anycloud.data.notification.liveupdate.database.LiveUpdateNotificationEntity
import com.baidaidai.anycloud.data.notification.liveupdate.database.LiveUpdateNotificationPositionEntity
import com.baidaidai.anycloud.data.notification.ongoing.database.OngoingNotificationDAO
import com.baidaidai.anycloud.data.notification.ongoing.database.OngoingNotificationEntity

@Database(
    entities = [
        OngoingNotificationEntity::class,
        LiveUpdateNotificationEntity::class,
        LiveUpdateNotificationPositionEntity::class,
        DailyTrackEntity::class,
        DailyCountEntity::class

        // 其他表
    ],
    version = 1
)
abstract class AnyCloudDataBase: RoomDatabase() {
    abstract fun ongoingNotificationDao(): OngoingNotificationDAO
    abstract fun liveUpdateNotificationDao(): LiveUpdateNotificationDAO
    abstract fun dailyTrackDao(): DailyTrackDao
    abstract fun dailyCountDao(): DailyCountDao

    //其他DAO
}
