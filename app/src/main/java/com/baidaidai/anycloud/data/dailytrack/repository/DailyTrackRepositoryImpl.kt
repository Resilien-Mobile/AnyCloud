package com.baidaidai.anycloud.data.dailytrack.repository

import androidx.room.withTransaction
import com.baidaidai.anycloud.data.dailytrack.database.DailyTrackEntity
import com.baidaidai.anycloud.data.database.AnyCloudDataBase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DailyTrackRepositoryImpl @Inject constructor(
    private val anyCloudDataBase: AnyCloudDataBase
) {
    private val dailyTrackDao = anyCloudDataBase.dailyTrackDao()

    /**
     * CRUD
     */

    // Create
    suspend fun addDailyTrackRecord(
        dateIndex: Int
    ) {
        require(dateIndex in 0..365) {
            "dateIndex must be between 0 and 365"
        }

        anyCloudDataBase.withTransaction {
            val dailyTrackRecordExists = hasDailyTrackRecord(dateIndex)

            if (dailyTrackRecordExists) {
                dailyTrackDao.updateDailyTrackScore(dateIndex)
            } else {
                val newDailyTrackEntity = DailyTrackEntity(
                    dateIndex = dateIndex,
                    dateScore = 1
                )

                dailyTrackDao.insertDailyTrack(newDailyTrackEntity)
            }
        }
    }

    // Update

    // Read
    suspend fun hasDailyTrackRecord(
        dateIndex: Int
    ): Boolean {
        val dailyTrackEntity = dailyTrackDao.findDailyTrackByDateIndex(dateIndex)
        val hasDailyTrackRecord = dailyTrackEntity != null

        return hasDailyTrackRecord
    }

    fun observeDailyTracks(): Flow<List<Int>> {
        val dailyTrackEntityFlow = dailyTrackDao.observeDailyTracks()
        val dailyTrackScoreListFlow = dailyTrackEntityFlow.map { dailyTrackEntityList ->
            val dailyTrackScoreList = MutableList(366) { 0 }

            dailyTrackEntityList.forEach { dailyTrackEntity ->
                if (dailyTrackEntity.dateIndex in dailyTrackScoreList.indices) {
                    val dateScore = Math.toIntExact(dailyTrackEntity.dateScore)

                    dailyTrackScoreList[dailyTrackEntity.dateIndex] = dateScore
                }
            }

            dailyTrackScoreList.toList()
        }

        return dailyTrackScoreListFlow
    }

    // Delete
    suspend fun deleteAllDailyTracks() {
        dailyTrackDao.deleteAllDailyTracks()
    }
}
