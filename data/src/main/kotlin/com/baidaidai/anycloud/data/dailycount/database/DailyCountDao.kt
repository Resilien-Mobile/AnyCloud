package com.baidaidai.anycloud.data.dailycount.database

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface DailyCountDao {
    /**
     * CRUD
    */

    // Create
    @Upsert
    suspend fun upsertDailyCount(
        dailyCountEntity: DailyCountEntity
    )

    // Update
    @Transaction
    suspend fun updateTotalDayCount(
        totalDayCount: Long
    ) {
        val dailyCountEntity = findDailyCount() ?: DailyCountEntity()
        val updatedDailyCountEntity = dailyCountEntity.copy(
            totalDayCount = totalDayCount
        )

        upsertDailyCount(updatedDailyCountEntity)
    }

    @Transaction
    suspend fun updateTotalPlanCount(
        totalPlanCount: Long
    ) {
        val dailyCountEntity = findDailyCount() ?: DailyCountEntity()
        val updatedDailyCountEntity = dailyCountEntity.copy(
            totalPlanCount = totalPlanCount
        )

        upsertDailyCount(updatedDailyCountEntity)
    }

    // Read
    @Query(
        """
        SELECT *
        FROM DailyCountEntity
        WHERE primaryKey = 114514
        """
    )
    suspend fun findDailyCount(): DailyCountEntity?

    @Query(
        """
        SELECT COALESCE(
            (SELECT totalDayCount FROM DailyCountEntity WHERE primaryKey = 114514),
            0
        )
        """
    )
    fun observeTotalDayCount(): Flow<Long>

    @Query(
        """
        SELECT COALESCE(
            (SELECT totalPlanCount FROM DailyCountEntity WHERE primaryKey = 114514),
            0
        )
        """
    )
    fun observeTotalPlanCount(): Flow<Long>

    // Delete
}
