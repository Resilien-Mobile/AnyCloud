package com.baidaidai.anycloud.ui.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.baidaidai.anycloud.application.setting.ObserveOngoingStyleEnabledUseCase
import com.baidaidai.anycloud.application.setting.SyncOngoingStyleEnabledUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class SettingScreenViewModel @Inject constructor(
    observeOngoingStyleEnabledUseCase: ObserveOngoingStyleEnabledUseCase,
    private val syncOngoingStyleEnabledUseCase: SyncOngoingStyleEnabledUseCase
) : ViewModel() {
    val isOngoingStyleEnabled: StateFlow<Boolean> = observeOngoingStyleEnabledUseCase().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = false
    )

    fun syncOngoingStyleEnabled(
        isEnabled: Boolean
    ) {
        syncOngoingStyleEnabledUseCase(
            isEnabled = isEnabled
        )
    }
}
