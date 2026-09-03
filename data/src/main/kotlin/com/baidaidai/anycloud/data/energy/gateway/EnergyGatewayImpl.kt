package com.baidaidai.anycloud.data.energy.gateway

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import android.util.Log
import com.baidaidai.anycloud.domain.energy.model.EnergyType
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import kotlin.math.abs

class EnergyGatewayImpl @Inject constructor(
    @param:ApplicationContext private val context: Context
) {
    private val batteryManager: BatteryManager =
        context.getSystemService(BatteryManager::class.java)

    private fun getRawAmpere(): Flow<Int> = flow {
        while (true) {
            emit(batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CURRENT_NOW))
            delay(50L)
        }
    }

    private fun getRawVoltage(): Flow<Int> = flow {
        while (true) {
            val batteryStatus = context.registerReceiver(
                null,
                IntentFilter(Intent.ACTION_BATTERY_CHANGED)
            )

            emit(batteryStatus?.getIntExtra(BatteryManager.EXTRA_VOLTAGE, 0) ?: 0)
            delay(50L)
        }
    }

    // Ampere still have trouble
    // I don't know if OEM will return
    // Milliampere or Microampere
    fun getCurrentMilliampere(): Flow<Double> {
        return getRawAmpere().map { rawAmpere ->
            Log.d("EnergyGateway", "rawAmpere = $rawAmpere")
            if (abs(rawAmpere) >= 3000) {
                rawAmpere / 1000.0
            } else {
                rawAmpere.toDouble()
            }
        }
    }

    fun getCurrentVoltage(): Flow<Double> {
        return getRawVoltage().map { rawVoltage ->
            Log.d("EnergyGateway", "rawVoltage = $rawVoltage")
            if (abs(rawVoltage) >= 1000) {
                rawVoltage / 1000.0
            } else {
                rawVoltage.toDouble()
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
