package com.baidaidai.anycloud.data.energy.gateway

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import android.util.Log
import com.baidaidai.anycloud.domain.energy.datasource.EnergyDataSource
import com.baidaidai.anycloud.domain.energy.model.AmpereUnit
import com.baidaidai.anycloud.domain.energy.model.EnergyType
import com.baidaidai.anycloud.domain.energy.model.VoltageUnit
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import javax.inject.Inject
import kotlin.math.abs

class EnergyGatewayImpl @Inject constructor(
    @param:ApplicationContext private val context: Context,
    private val energyDataSource: EnergyDataSource
) {
    // Ampere still have trouble
    // I don't know if OEM will return
    // Milliampere or Microampere
    var ampereUnit: AmpereUnit
    var voltageUnit: VoltageUnit

    init {
        runBlocking {
            val rowAmpereValue = abs(
                energyDataSource
                    .observeRawAmpere()
                    .first{
                        it != 0
                    }
            )

            // 数值越大，越可能是更小的微安单位。
            // 避免将 1000000 μA 误判为 1000000 mA。
            ampereUnit = if (rowAmpereValue in 1..<10000) {
                AmpereUnit.MILLIAMPERE
            } else {
                AmpereUnit.MICROAMPERE
            }
        }
        runBlocking {
            val rowVoltageValue = abs(
                energyDataSource
                    .observeRawVoltage()
                    .first{
                        it != 0
                    }
            )

            // 数值越大，越可能是更小的微伏单位。
            // 避免将 5000000 μV 误判为 5000000 mV。
            voltageUnit = if (rowVoltageValue in 1..<100000) {
                VoltageUnit.MILLIVOLTAGE
            } else {
                VoltageUnit.MICROVOLTAGE
            }
        }
    }

    fun getCurrentMilliampere(): Flow<Double> {
        return energyDataSource.observeRawAmpere().map { rawAmpere ->
            Log.d("EnergyGateway", "rawAmpere = $rawAmpere")
            when (ampereUnit) {
                AmpereUnit.MILLIAMPERE -> rawAmpere.toDouble()
                AmpereUnit.MICROAMPERE -> rawAmpere / 1000.0
            }
        }
    }

    fun getCurrentVoltage(): Flow<Double> {
        return energyDataSource.observeRawVoltage().map { rawVoltage ->
            Log.d("EnergyGateway", "rawVoltage = $rawVoltage")
            when (voltageUnit) {
                VoltageUnit.MILLIVOLTAGE -> rawVoltage / 1000.0
                VoltageUnit.MICROVOLTAGE -> rawVoltage / 1_000_000.0
            }
        }
    }

    fun getCurrentWatt(): Flow<Double> {
        return combine(
            getCurrentVoltage(),
            getCurrentMilliampere()
        ) { voltage, milliampere ->
            val watt = voltage * milliampere / 1000.0
            Log.d("EnergyGateway", "watt = $watt")
            watt
        }
    }

    fun getCurrentAdapterType(): Flow<EnergyType> = flow {
        // 不能使用 Callback Flow ，有异常
        while (true) {
            val batteryStatus = context.registerReceiver(
                null,
                IntentFilter(Intent.ACTION_BATTERY_CHANGED)
            )

            val plugged = batteryStatus?.getIntExtra(BatteryManager.EXTRA_PLUGGED, 0)
            val adapterType = when (plugged) {
                null -> EnergyType.UNKNOW
                0 -> EnergyType.BATTERY
                BatteryManager.BATTERY_PLUGGED_AC -> EnergyType.AC
                BatteryManager.BATTERY_PLUGGED_DOCK -> EnergyType.DOCK
                BatteryManager.BATTERY_PLUGGED_USB -> EnergyType.USB
                BatteryManager.BATTERY_PLUGGED_WIRELESS -> EnergyType.WIRELESS
                else -> EnergyType.UNKNOW
            }

            emit(adapterType)
            delay(1_000L)
        }
    }

    fun getCurrentBatteryPercentage(): Flow<Int> = flow {
        while (true) {
            val batteryStatus = context.registerReceiver(
                null,
                IntentFilter(Intent.ACTION_BATTERY_CHANGED)
            )

            val level = batteryStatus?.getIntExtra(BatteryManager.EXTRA_LEVEL, -1) ?: -1
            val scale = batteryStatus?.getIntExtra(BatteryManager.EXTRA_SCALE, -1) ?: -1
            val percentage = if (level >= 0 && scale > 0) {
                level * 100 / scale
            } else {
                0
            }

            emit(percentage)
            delay(1_000L)
        }
    }
}
