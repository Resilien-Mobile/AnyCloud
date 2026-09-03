package com.baidaidai.anycloud.application.dailytrack

import com.baidaidai.anycloud.data.dailytrack.repository.DailyTrackRepositoryImpl
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveDailyTracksUseCase @Inject constructor(
    private val dailyTrackRepositoryImpl: DailyTrackRepositoryImpl
) {
    operator fun invoke(): Flow<List<Int>> {
        val dailyTrackScoreListFlow = dailyTrackRepositoryImpl.observeDailyTracks()

        return dailyTrackScoreListFlow
    }
}
