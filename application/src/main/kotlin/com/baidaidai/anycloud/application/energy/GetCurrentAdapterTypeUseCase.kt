package com.baidaidai.anycloud.application.energy

import com.baidaidai.anycloud.data.energy.gateway.EnergyGatewayImpl
import com.baidaidai.anycloud.domain.energy.model.EnergyType
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCurrentAdapterTypeUseCase @Inject constructor(
    private val energyGatewayImpl: EnergyGatewayImpl
) {
    operator fun invoke(): Flow<EnergyType> {
        return energyGatewayImpl.getCurrentAdapterType()
    }
}
