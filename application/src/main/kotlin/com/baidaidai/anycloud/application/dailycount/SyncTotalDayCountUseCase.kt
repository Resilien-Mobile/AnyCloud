package com.baidaidai.anycloud.application.dailycount

import com.baidaidai.anycloud.data.dailycount.repository.DailyCountRepositoryImpl
import com.baidaidai.anycloud.data.dailytrack.repository.DailyTrackRepositoryImpl
import java.time.LocalDate
import javax.inject.Inject

class SyncTotalDayCountUseCase @Inject constructor(
    private val dailyCountRepositoryImpl: DailyCountRepositoryImpl,
    private val dailyTrackRepositoryImpl: DailyTrackRepositoryImpl
) {
    suspend operator fun invoke() {

        val dateIndex = LocalDate.now().dayOfYear - 1
        val hasDailyTrackRecord = dailyTrackRepositoryImpl.hasDailyTrackRecord(dateIndex)

        if (hasDailyTrackRecord) return

        dailyCountRepositoryImpl.syncTotalDayCount(
            totalDayCount = 1
        )
    }
}
