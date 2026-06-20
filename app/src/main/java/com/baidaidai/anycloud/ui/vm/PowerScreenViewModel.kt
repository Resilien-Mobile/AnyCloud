package com.baidaidai.anycloud.ui.vm

import android.Manifest
import android.content.Context
import androidx.annotation.RequiresPermission
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.baidaidai.anycloud.R
import com.baidaidai.anycloud.application.energy.GetCurrentAdapterTypeUseCase
import com.baidaidai.anycloud.application.energy.GetCurrentBatteryPercentageUseCase
import com.baidaidai.anycloud.application.energy.GetCurrentMilliampereUseCase
import com.baidaidai.anycloud.application.energy.GetCurrentVoltageUseCase
import com.baidaidai.anycloud.application.energy.GetCurrentWattUseCase
import com.baidaidai.anycloud.application.notification.PushLiveActivityUseCase
import com.baidaidai.anycloud.domain.energy.model.EnergyType
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PowerScreenViewModel @Inject constructor(
    @param:ApplicationContext private val context: Context,
    getCurrentAdapterTypeUseCase: GetCurrentAdapterTypeUseCase,
    getCurrentBatteryPercentageUseCase: GetCurrentBatteryPercentageUseCase,
    getCurrentMilliampereUseCase: GetCurrentMilliampereUseCase,
    getCurrentVoltageUseCase: GetCurrentVoltageUseCase,
    getCurrentWattUseCase: GetCurrentWattUseCase,
    private val pushLiveActivityUseCase: PushLiveActivityUseCase
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

    private var powerCloudJob: Job? = null

    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    fun enablePowerCloud(){
        val powerCloudNotificationID = context.getString(R.string.power_cloud_notification_id).toInt()

        if (powerCloudJob?.isActive == true) {
            return
        }

        powerCloudJob = viewModelScope.launch {
            while (isActive) {
                pushLiveActivityUseCase(
                    notificationTitle = "Current Watt",
                    notificationContent = String.format("%.2f", currentWatt.value),
                    notificationID = powerCloudNotificationID
                )
                delay(1_000L)
            }
        }
    }

}
