package com.baidaidai.anycloud.data.dailycount.repository

import androidx.room.withTransaction
import com.baidaidai.anycloud.data.dailycount.database.DailyCountEntity
import com.baidaidai.anycloud.data.database.AnyCloudDataBase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DailyCountRepositoryImpl @Inject constructor(
    private val anyCloudDataBase: AnyCloudDataBase
) {
    private val dailyCountDao = anyCloudDataBase.dailyCountDao()

    /**
     * CRUD
     */

    // Create

    // Update
    suspend fun syncTotalDayCount(
        totalDayCount: Long
    ) {
        anyCloudDataBase.withTransaction {
            val dailyCountEntity = findDailyCount()
            val updatedTotalDayCount = dailyCountEntity.totalDayCount + totalDayCount
            val updatedDailyCountEntity = dailyCountEntity.copy(
                totalDayCount = updatedTotalDayCount
            )

            dailyCountDao.upsertDailyCount(updatedDailyCountEntity)
        }
    }

    suspend fun syncTotalPlanCount(
        totalPlanCount: Long
    ) {
        anyCloudDataBase.withTransaction {
            val dailyCountEntity = findDailyCount()
            val updatedTotalPlanCount = dailyCountEntity.totalPlanCount + totalPlanCount
            val updatedDailyCountEntity = dailyCountEntity.copy(
                totalPlanCount = updatedTotalPlanCount
            )

            dailyCountDao.upsertDailyCount(updatedDailyCountEntity)
        }
    }

    private suspend fun findDailyCount(): DailyCountEntity {
        val dailyCountEntity = dailyCountDao.findDailyCount() ?: DailyCountEntity()

        return dailyCountEntity
    }

    // Read
    fun observeTotalDayCount(): Flow<Long> {
        val totalDayCountFlow = dailyCountDao.observeTotalDayCount()

        return totalDayCountFlow
    }

    fun observeTotalPlanCount(): Flow<Long> {
        val totalPlanCountFlow = dailyCountDao.observeTotalPlanCount()

        return totalPlanCountFlow
    }

    // Delete
}
