package com.baidaidai.anycloud.application.energy

import com.baidaidai.anycloud.data.energy.gateway.EnergyGatewayImpl
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCurrentBatteryPercentageUseCase @Inject constructor(
    private val energyGatewayImpl: EnergyGatewayImpl
) {
    operator fun invoke(): Flow<Int> {
        return energyGatewayImpl.getCurrentBatteryPercentage()
    }
}
