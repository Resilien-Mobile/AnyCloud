package com.baidaidai.anycloud.ui.viewmodel

import android.Manifest
import android.content.Context
import androidx.annotation.RequiresPermission
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.baidaidai.anycloud.ui.R
import com.baidaidai.anycloud.application.energy.GetCurrentAdapterTypeUseCase
import com.baidaidai.anycloud.application.energy.GetCurrentBatteryPercentageUseCase
import com.baidaidai.anycloud.application.energy.GetCurrentMilliampereUseCase
import com.baidaidai.anycloud.application.energy.GetCurrentVoltageUseCase
import com.baidaidai.anycloud.application.energy.GetCurrentWattUseCase
import com.baidaidai.anycloud.application.notification.liveupdate.DisablePowerCloudNotificationUseCase
import com.baidaidai.anycloud.application.notification.liveupdate.EnablePowerCloudNotificationUseCase
import com.baidaidai.anycloud.domain.energy.model.EnergyType
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
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
    private val enablePowerCloudNotificationUseCase: EnablePowerCloudNotificationUseCase,
    private val disablePowerCloudNotificationUseCase: DisablePowerCloudNotificationUseCase
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
    private val _isPowerCloudEnabled = MutableStateFlow(false)
    val isPowerCloudEnabled: StateFlow<Boolean> = _isPowerCloudEnabled.asStateFlow()

    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    fun enablePowerCloud(){
        val powerCloudNotificationID = context.getString(R.string.power_cloud_notification_id).toInt()

        if (powerCloudJob?.isActive == true) {
            return
        }

        _isPowerCloudEnabled.value = true
        powerCloudJob = viewModelScope.launch {
            while (isActive) {
                enablePowerCloudNotificationUseCase(
                    notificationTitle = "Current Watt",
                    notificationContent = String.format("%.2f", currentWatt.value),
                    notificationID = powerCloudNotificationID
                )
                delay(1_000L)
            }
        }
    }

    fun disablePowerCloud() {
        val powerCloudNotificationID = context.getString(R.string.power_cloud_notification_id).toInt()

        powerCloudJob?.cancel()
        powerCloudJob = null
        disablePowerCloudNotificationUseCase(
            notificationId = powerCloudNotificationID
        )
        _isPowerCloudEnabled.value = false
    }

}
