package com.baidaidai.anycloud.test.data.energy

import android.content.Context
import com.baidaidai.anycloud.data.energy.gateway.EnergyGatewayImpl
import com.baidaidai.anycloud.domain.energy.datasource.EnergyDataSource
import com.baidaidai.anycloud.domain.energy.model.AmpereUnit
import io.mockk.mockk
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import org.junit.Assert.*
import org.junit.Test

class EnergyGatewayTest {

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

    @Test
    fun testInitCorrectUnit(){

        val context: Context = mockk()

        val energyGateway = EnergyGatewayImpl(
            context = context,
            energyDataSource = FakeEnergyDataSource()
        )

        assertEquals(AmpereUnit.MICROAMPERE,energyGateway.ampereUnit)

    }





}
