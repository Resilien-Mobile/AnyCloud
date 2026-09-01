package com.baidaidai.anycloud.application.dailycount

import com.baidaidai.anycloud.data.dailycount.repository.DailyCountRepositoryImpl
import javax.inject.Inject

class SyncTotalDayCountUseCase @Inject constructor(
    private val dailyCountRepositoryImpl: DailyCountRepositoryImpl
) {
    suspend operator fun invoke(
        totalDayCount: Long = 1
    ) {
        dailyCountRepositoryImpl.syncTotalDayCount(totalDayCount)
    }
}
