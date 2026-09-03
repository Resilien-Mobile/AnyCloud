package com.baidaidai.anycloud.application.dailycount

import com.baidaidai.anycloud.data.dailycount.repository.DailyCountRepositoryImpl
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveTotalDayCountUseCase @Inject constructor(
    private val dailyCountRepositoryImpl: DailyCountRepositoryImpl
) {
    operator fun invoke(): Flow<Long> {
        val totalDayCountFlow = dailyCountRepositoryImpl.observeTotalDayCount()

        return totalDayCountFlow
    }
}
