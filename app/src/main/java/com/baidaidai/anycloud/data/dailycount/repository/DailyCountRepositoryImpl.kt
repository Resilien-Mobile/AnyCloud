package com.baidaidai.anycloud.data.dailycount.repository

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
        dailyCountDao.updateTotalDayCount(totalDayCount)
    }

    suspend fun syncTotalPlanCount(
        totalPlanCount: Long
    ) {
        dailyCountDao.updateTotalPlanCount(totalPlanCount)
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
