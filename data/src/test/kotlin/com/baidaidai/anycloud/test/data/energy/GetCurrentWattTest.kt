package com.baidaidai.anycloud.test.data.energy

import android.content.Context
import android.util.Log
import com.baidaidai.anycloud.data.energy.gateway.EnergyGatewayImpl
import com.baidaidai.anycloud.domain.energy.datasource.EnergyDataSource
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GetCurrentWattTest {

    class FakeEnergyDataSource(
        private val rawAmpere: Int = 1_000_000,
        private val rawVoltage: Int = 5_000,
    ) : EnergyDataSource {
        override fun observeRawAmpere(): Flow<Int> {
            return flowOf(rawAmpere)
        }

        override fun observeRawVoltage(): Flow<Int> {
            return flowOf(rawVoltage)
        }
    }

    @Before
    fun start(){
        mockkStatic(Log::class)

        every {
            Log.d(any(), any())
        } returns 0
    }

    @Test
    fun testGetCurrentWatt() = runBlocking {
        val context: Context = mockk()
        val watt = EnergyGatewayImpl(
            context = context,
            energyDataSource = FakeEnergyDataSource()
        )
            .getCurrentWatt()
            .first()

        assertEquals(5.0, watt, 0.0)
    }

}