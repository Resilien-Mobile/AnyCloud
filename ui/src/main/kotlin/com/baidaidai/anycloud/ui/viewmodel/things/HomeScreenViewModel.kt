package com.baidaidai.anycloud.ui.viewmodel.things

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import androidx.annotation.RequiresPermission
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.baidaidai.anycloud.application.voiceprocessor.ResolveVoiceRecordingTextUseCase
import com.baidaidai.anycloud.application.voiceprocessor.StartVoiceRecordingUseCase
import com.baidaidai.anycloud.application.notification.ongoing.PushOneOngoingNotificationConfigUseCase
import com.baidaidai.anycloud.application.notification.ongoing.DeleteOneOngoingNotificationConfigUseCase
import com.baidaidai.anycloud.application.notification.ongoing.GetAllOngoingNotificationConfigUseCase
import com.baidaidai.anycloud.application.notification.ongoing.UpdateOneOngoingNotificationTaskFinishedUseCase
import com.baidaidai.anycloud.domain.notification.model.NotificationConfig
import com.baidaidai.anycloud.ui.R
import com.github.michaelbull.result.onErr
import com.github.michaelbull.result.onOk
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class HomeScreenUiState(
    val isVoiceRecording: Boolean = false,
    val conversationContent: String = "",
)

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    @param:ApplicationContext private val context: Context,
    private val pushOneOngoingNotificationConfigUseCase: PushOneOngoingNotificationConfigUseCase,
    private val deleteOneOngoingNotificationConfigUseCase: DeleteOneOngoingNotificationConfigUseCase,
    private val getAllOnGoingNotificationConfigUseCase: GetAllOngoingNotificationConfigUseCase,
    private val updateOneOngoingNotificationTaskFinishedUseCase: UpdateOneOngoingNotificationTaskFinishedUseCase,
    private val startVoiceRecordingUseCase: StartVoiceRecordingUseCase,
    private val resolveVoiceRecordingTextUseCase: ResolveVoiceRecordingTextUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeScreenUiState())
    val uiState: StateFlow<HomeScreenUiState> = _uiState.asStateFlow()

    val notificationConfigList = getAllOnGoingNotificationConfigUseCase().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = emptyList()
    )

    // CUD
    @SuppressLint("MissingPermission")
    fun switchVoiceRecordStatus(){

        viewModelScope.launch {

            if (_uiState.value.isVoiceRecording) stopVoiceRecording() else startVoiceRecording()

        }

    }

    fun clearConversationContent() {

        _uiState.update { uiState ->
            uiState.copy(
                conversationContent = ""
            )
        }

    }

    @RequiresPermission(Manifest.permission.RECORD_AUDIO)
    private suspend fun startVoiceRecording() {

        if (_uiState.value.isVoiceRecording) return

        startVoiceRecordingUseCase()
            .onOk {
                _uiState.update { uiState ->
                    uiState.copy(
                        isVoiceRecording = true
                    )
                }
            }

    }

    private suspend fun stopVoiceRecording() {

        if (!_uiState.value.isVoiceRecording) return

        _uiState.value = _uiState.value.copy(
            isVoiceRecording = false
        )

        resolveVoiceRecordingTextUseCase()
            .onOk { conversationContent ->
                _uiState.update { uiState ->
                    uiState.copy(
                        isVoiceRecording = false,
                        conversationContent = conversationContent
                    )
                }
            }
            .onErr { error ->
                _uiState.update { uiState ->
                    uiState.copy(
                        isVoiceRecording = false,
                    )
                }
            }

    }

    // Create
    @SuppressLint("MissingPermission")
    fun createOneNotificationConfig(
        notificationTitle: String = context.getString(R.string.home_screen_notification_default_title),
        notificationContent: String,
    ) {
        viewModelScope.launch {
            pushOneOngoingNotificationConfigUseCase(
                notificationTitle = notificationTitle,
                notificationContent = notificationContent
            )
        }
    }
    // Update
    fun updateOneNotificationTaskFinished(
        notificationConfig: NotificationConfig,
        isTaskFinished: Boolean = false
    ) {
        val unixTimeStamp = notificationConfig.unixTimeStamp

        viewModelScope.launch {
            updateOneOngoingNotificationTaskFinishedUseCase(
                unixTimeStamp = unixTimeStamp,
                isTaskFinished = isTaskFinished
            )
        }
    }

    //Delete
    fun deleteOneNotificationConfig(
        notificationConfig: NotificationConfig
    ) {
        viewModelScope.launch {
            deleteOneOngoingNotificationConfigUseCase(notificationConfig)
        }
    }

}
