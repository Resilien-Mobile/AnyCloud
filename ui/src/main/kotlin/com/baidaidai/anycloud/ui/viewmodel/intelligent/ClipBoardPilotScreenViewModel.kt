package com.baidaidai.anycloud.ui.viewmodel.intelligent

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.baidaidai.anycloud.application.shizuku.ObserveShizukuStatusUseCase
import com.baidaidai.anycloud.domain.shizuku.ShizukuStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class ClipBoardPilotScreenViewModel @Inject constructor(
    observeShizukuStatusUseCase: ObserveShizukuStatusUseCase
) : ViewModel() {

    val shizukuStatus = observeShizukuStatusUseCase().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = ShizukuStatus.UNAVAILABLE
    )
}