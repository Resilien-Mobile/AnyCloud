package com.baidaidai.anycloud.application.dailycount

import com.baidaidai.anycloud.data.dailycount.repository.DailyCountRepositoryImpl
import javax.inject.Inject

class SyncTotalPlanCountUseCase @Inject constructor(
    private val dailyCountRepositoryImpl: DailyCountRepositoryImpl
) {
    suspend operator fun invoke(
        totalPlanCount: Long = 1
    ) {
        dailyCountRepositoryImpl.syncTotalPlanCount(totalPlanCount)
    }
}