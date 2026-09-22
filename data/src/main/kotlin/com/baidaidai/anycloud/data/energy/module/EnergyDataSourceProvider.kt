package com.baidaidai.anycloud.data.energy.module

import com.baidaidai.anycloud.data.energy.datasource.EnergyDataSourceImpl
import com.baidaidai.anycloud.domain.energy.datasource.EnergyDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object EnergyDataSourceProvider {

    @Provides
    @Singleton
    fun provideEnergyDataSource(
        energyDataSourceImpl: EnergyDataSourceImpl
    ): EnergyDataSource {
        return energyDataSourceImpl
    }
}