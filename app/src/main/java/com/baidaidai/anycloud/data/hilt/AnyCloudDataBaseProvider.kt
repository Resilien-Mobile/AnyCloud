package com.baidaidai.anycloud.data.hilt

import android.content.Context
import androidx.room.Room
import com.baidaidai.anycloud.data.database.AnyCloudDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AnyCloudDataBaseProvider {

    @Provides
    @Singleton
    fun provideAnyCloudDataBase(
        @ApplicationContext
        context: Context
    ): AnyCloudDataBase{
        return Room.databaseBuilder(
            context = context,
            klass = AnyCloudDataBase::class.java,
            name = "AnyCloudDataBase"
        ).build()
    }

}