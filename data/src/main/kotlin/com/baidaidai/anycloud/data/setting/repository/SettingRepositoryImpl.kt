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
    private val clipboardListeningEnabledKey = "is_clipboard_listening_enabled"
    private val isOngoingStyleEnabledFlow = MutableStateFlow(
        sharedPreferences.getBoolean(
            ongoingStyleEnabledKey,
            false
        )
    )
    private val isClipboardListeningEnabledFlow = MutableStateFlow(
        sharedPreferences.getBoolean(
            clipboardListeningEnabledKey,
            false
        )
    )

    fun observeOngoingStyleEnabled(): Flow<Boolean> {
        val ongoingStyleEnabledFlow = isOngoingStyleEnabledFlow.asStateFlow()

        return ongoingStyleEnabledFlow
    }

    fun observeClipboardListeningEnabled(): Flow<Boolean> {
        val clipboardListeningEnabledFlow = isClipboardListeningEnabledFlow.asStateFlow()

        return clipboardListeningEnabledFlow
    }

    fun syncOngoingStyleEnabled(
        isEnabled: Boolean
    ) {
        isOngoingStyleEnabledFlow.value = isEnabled

        sharedPreferences.edit {
            putBoolean(ongoingStyleEnabledKey, isEnabled)
        }
    }

    fun syncClipboardListeningEnabled(
        isEnabled: Boolean
    ) {
        isClipboardListeningEnabledFlow.value = isEnabled

        sharedPreferences.edit {
            putBoolean(clipboardListeningEnabledKey, isEnabled)
        }
    }
}
