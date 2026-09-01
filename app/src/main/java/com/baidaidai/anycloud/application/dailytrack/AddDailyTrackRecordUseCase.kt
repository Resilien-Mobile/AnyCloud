package com.baidaidai.anycloud.application.dailytrack

import com.baidaidai.anycloud.data.dailytrack.repository.DailyTrackRepositoryImpl
import com.baidaidai.anycloud.domain.dailytrack.DailyTrackConfig
import javax.inject.Inject

class AddDailyTrackRecordUseCase @Inject constructor(
    private val dailyTrackRepositoryImpl: DailyTrackRepositoryImpl
) {
    suspend operator fun invoke(
        dailyTrackConfig: DailyTrackConfig
    ) {
        dailyTrackRepositoryImpl.addDailyTrackRecord(dailyTrackConfig)
    }
}
