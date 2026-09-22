package com.baidaidai.anycloud.data.energy.datasource

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import com.baidaidai.anycloud.domain.energy.datasource.EnergyDataSource
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class EnergyDataSourceImpl @Inject constructor(
    @param:ApplicationContext private val context: Context
) : EnergyDataSource {
    private val batteryManager: BatteryManager =
        context.getSystemService(BatteryManager::class.java)

    override fun observeRawAmpere(): Flow<Int> = flow {
        while (true) {
            emit(batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CURRENT_NOW))
            delay(50L)
        }
    }

    override fun observeRawVoltage(): Flow<Int> = flow {
        while (true) {
            val batteryStatus = context.registerReceiver(
                null,
                IntentFilter(Intent.ACTION_BATTERY_CHANGED)
            )

            emit(batteryStatus?.getIntExtra(BatteryManager.EXTRA_VOLTAGE, 0) ?: 0)
            delay(50L)
        }
    }
}
