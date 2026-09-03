package com.baidaidai.anycloud.data.setting.repository

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton
import androidx.core.content.edit

@Singleton
class SettingRepositoryImpl @Inject constructor(
    @param:ApplicationContext private val context: Context
) {
    private val sharedPreferences = context.getSharedPreferences(
        "any_cloud_settings",
        Context.MODE_PRIVATE
    )
    private val ongoingStyleEnabledKey = "is_ongoing_style_enabled"
    private val isOngoingStyleEnabledFlow = MutableStateFlow(
        sharedPreferences.getBoolean(
            ongoingStyleEnabledKey,
            false
        )
    )

    fun observeOngoingStyleEnabled(): Flow<Boolean> {
        val ongoingStyleEnabledFlow = isOngoingStyleEnabledFlow.asStateFlow()

        return ongoingStyleEnabledFlow
    }

    fun syncOngoingStyleEnabled(
        isEnabled: Boolean
    ) {
        isOngoingStyleEnabledFlow.value = isEnabled

        sharedPreferences.edit {
            putBoolean(ongoingStyleEnabledKey, isEnabled)
        }
    }
}
