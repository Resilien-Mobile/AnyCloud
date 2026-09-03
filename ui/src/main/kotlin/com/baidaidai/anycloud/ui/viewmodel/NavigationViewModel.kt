package com.baidaidai.anycloud.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.baidaidai.anycloud.application.dailycount.ObserveTotalDayCountUseCase
import com.baidaidai.anycloud.application.dailycount.ObserveTotalPlanCountUseCase
import com.baidaidai.anycloud.application.dailytrack.ObserveDailyTracksUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class NavigationViewModel @Inject constructor(
    observeDailyTracksUseCase: ObserveDailyTracksUseCase,
    observeTotalDayCountUseCase: ObserveTotalDayCountUseCase,
    observeTotalPlanCountUseCase: ObserveTotalPlanCountUseCase
) : ViewModel() {
    val dailyTrackScoreList = observeDailyTracksUseCase().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = List(366) { 0 }
    )

    val totalDayCount = observeTotalDayCountUseCase().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = 0L
    )

    val totalPlanCount = observeTotalPlanCountUseCase().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = 0L
    )
}
