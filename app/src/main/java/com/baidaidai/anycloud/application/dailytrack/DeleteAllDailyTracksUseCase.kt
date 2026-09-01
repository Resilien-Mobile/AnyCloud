package com.baidaidai.anycloud.application.dailytrack

import com.baidaidai.anycloud.data.dailytrack.repository.DailyTrackRepositoryImpl
import javax.inject.Inject

class DeleteAllDailyTracksUseCase @Inject constructor(
    private val dailyTrackRepositoryImpl: DailyTrackRepositoryImpl
) {
    suspend operator fun invoke() {
        dailyTrackRepositoryImpl.deleteAllDailyTracks()
    }
}
