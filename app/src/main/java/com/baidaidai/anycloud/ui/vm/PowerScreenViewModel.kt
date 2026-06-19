package com.baidaidai.anycloud.ui.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.baidaidai.anycloud.application.energy.GetCurrentAdapterTypeUseCase
import com.baidaidai.anycloud.application.energy.GetCurrentBatteryPercentageUseCase
import com.baidaidai.anycloud.application.energy.GetCurrentMilliampereUseCase
import com.baidaidai.anycloud.application.energy.GetCurrentVoltageUseCase
import com.baidaidai.anycloud.application.energy.GetCurrentWattUseCase
import com.baidaidai.anycloud.domain.energy.model.EnergyType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class PowerScreenViewModel @Inject constructor(
    getCurrentAdapterTypeUseCase: GetCurrentAdapterTypeUseCase,
    getCurrentBatteryPercentageUseCase: GetCurrentBatteryPercentageUseCase,
    getCurrentMilliampereUseCase: GetCurrentMilliampereUseCase,
    getCurrentVoltageUseCase: GetCurrentVoltageUseCase,
    getCurrentWattUseCase: GetCurrentWattUseCase
) : ViewModel() {
    val currentBatteryPercentage: StateFlow<Int> =
        getCurrentBatteryPercentageUseCase().stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = 0
        )

    val currentAdapterType: StateFlow<EnergyType> =
        getCurrentAdapterTypeUseCase().stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = EnergyType.UNKNOW
        )

    val currentMilliampere: StateFlow<Double> =
        getCurrentMilliampereUseCase().stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = 0.0
        )

    val currentVoltage: StateFlow<Double> =
        getCurrentVoltageUseCase().stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = 0.0
        )

    val currentWatt: StateFlow<Double> =
        getCurrentWattUseCase().stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = 0.0
        )

}
