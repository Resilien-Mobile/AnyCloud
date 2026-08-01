package com.baidaidai.anycloud.ui.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.baidaidai.anycloud.application.notification.ongoing.DeleteOneOngoingNotificationConfigUseCase
import com.baidaidai.anycloud.application.notification.ongoing.GetAllOngoingNotificationConfigUseCase
import com.baidaidai.anycloud.application.notification.ongoing.PushOneOngoingNotificationConfigUseCase
import com.baidaidai.anycloud.application.notification.ongoing.UpdateOneOngoingNotificationTaskFinishedUseCase
import com.baidaidai.anycloud.domain.notification.model.NotificationConfig
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskScreenViewModel @Inject constructor(
    private val pushOneOngoingNotificationConfigUseCase: PushOneOngoingNotificationConfigUseCase,
    private val deleteOneOngoingNotificationConfigUseCase: DeleteOneOngoingNotificationConfigUseCase,
    private val getAllOnGoingNotificationConfigUseCase: GetAllOngoingNotificationConfigUseCase,
    private val updateOneOngoingNotificationTaskFinishedUseCase: UpdateOneOngoingNotificationTaskFinishedUseCase
): ViewModel() {

    val notificationConfigList = getAllOnGoingNotificationConfigUseCase().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = emptyList()
    )

    // CUD

    // Create
    fun createOneNotificationConfig(
        notificationTitle: String = "Task Cloud",
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