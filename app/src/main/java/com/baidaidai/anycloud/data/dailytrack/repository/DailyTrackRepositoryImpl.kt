package com.baidaidai.anycloud.data.dailytrack.repository

import com.baidaidai.anycloud.data.dailytrack.mapper.toDailyTrackConfig
import com.baidaidai.anycloud.data.dailytrack.mapper.toDailyTrackEntity
import com.baidaidai.anycloud.data.database.AnyCloudDataBase
import com.baidaidai.anycloud.domain.dailytrack.DailyTrackConfig
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
        dailyTrackConfig: DailyTrackConfig
    ) {
        val dailyTrackEntity = dailyTrackConfig.toDailyTrackEntity()

        dailyTrackDao.insertDailyTrack(dailyTrackEntity)
    }

    // Update

    // Read
    fun observeDailyTracks(): Flow<List<Int>> {
        val dailyTrackEntityFlow = dailyTrackDao.observeDailyTracks()
        val dailyTrackConfigFlow = dailyTrackEntityFlow.map { dailyTrackEntityList ->
            dailyTrackEntityList.map { dailyTrackEntity ->
                dailyTrackEntity.toDailyTrackConfig()
            }
        }
        val dailyTrackScoreListFlow = dailyTrackConfigFlow.map { dailyTrackConfigList ->
            val dailyTrackScoreList = MutableList(366) { 0 }

            dailyTrackConfigList.forEach { dailyTrackConfig ->
                if (dailyTrackConfig.dateIndex in dailyTrackScoreList.indices) {
                    val dateScore = Math.toIntExact(dailyTrackConfig.dateScore)

                    dailyTrackScoreList[dailyTrackConfig.dateIndex] = dateScore
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
