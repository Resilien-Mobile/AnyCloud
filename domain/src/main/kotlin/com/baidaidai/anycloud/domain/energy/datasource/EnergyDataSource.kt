package com.baidaidai.anycloud.domain.energy.datasource

import kotlinx.coroutines.flow.Flow

interface EnergyDataSource {

    fun observeRawAmpere(): Flow<Int>

    fun observeRawVoltage(): Flow<Int>
}
