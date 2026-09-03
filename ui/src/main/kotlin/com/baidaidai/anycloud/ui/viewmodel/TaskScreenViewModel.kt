package com.baidaidai.anycloud.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.baidaidai.anycloud.application.notification.liveupdate.AddOneLiveUpdateNotificationUseCase
import com.baidaidai.anycloud.application.notification.liveupdate.DeleteOneLiveUpdateNotificationUseCase
import com.baidaidai.anycloud.application.notification.liveupdate.GetAllLiveUpdateNotificationUseCase
import com.baidaidai.anycloud.application.notification.liveupdate.PushTaskCloudNotificationUseCase
import com.baidaidai.anycloud.application.notification.liveupdate.UpdateOneLiveUpdateNotificationPositionUseCase
import com.baidaidai.anycloud.application.notification.liveupdate.UpdateOneLiveUpdateNotificationTaskFinishedUseCase
import com.baidaidai.anycloud.domain.notification.model.NotificationConfig
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskScreenViewModel @Inject constructor(
    private val addOneLiveUpdateNotificationUseCase: AddOneLiveUpdateNotificationUseCase,
    private val deleteOneLiveUpdateNotificationUseCase: DeleteOneLiveUpdateNotificationUseCase,
    private val getAllLiveUpdateNotificationUseCase: GetAllLiveUpdateNotificationUseCase,
    private val updateOneLiveUpdateNotificationTaskFinishedUseCase: UpdateOneLiveUpdateNotificationTaskFinishedUseCase,
    private val pushTaskCloudNotificationUseCase: PushTaskCloudNotificationUseCase,
    private val updateOneLiveUpdateNotificationPositionUseCase: UpdateOneLiveUpdateNotificationPositionUseCase
): ViewModel() {

    init {
        initLiveUpdateNotification()
    }

    val notificationConfigList = getAllLiveUpdateNotificationUseCase().stateIn(
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
            addOneLiveUpdateNotificationUseCase(
                notificationTitle = notificationTitle,
                notificationContent = notificationContent
            )
            pushTaskCloudNotificationUseCase(notificationTitle = notificationTitle)
        }
    }
    // Update
    fun updateOneNotificationTaskFinished(
        notificationConfig: NotificationConfig,
        isTaskFinished: Boolean = false
    ) {
        val unixTimeStamp = notificationConfig.unixTimeStamp
        val notificationTitle = notificationConfig.notificationTitle

        viewModelScope.launch {
            updateOneLiveUpdateNotificationTaskFinishedUseCase(
                unixTimeStamp = unixTimeStamp,
                isTaskFinished = isTaskFinished
            )
            pushTaskCloudNotificationUseCase(notificationTitle = notificationTitle)
        }
    }

    fun updateOneNotificationPosition(
        notificationConfig: NotificationConfig,
        insertionIndex: Int
    ) {
        val unixTimeStamp = notificationConfig.unixTimeStamp

        viewModelScope.launch {
            updateOneLiveUpdateNotificationPositionUseCase(
                unixTimeStamp = unixTimeStamp,
                insertionIndex = insertionIndex
            )
        }
    }

    //Delete
    fun deleteOneNotificationConfig(
        notificationConfig: NotificationConfig
    ) {
        val notificationTitle = notificationConfig.notificationTitle

        viewModelScope.launch {
            deleteOneLiveUpdateNotificationUseCase(notificationConfig)
            pushTaskCloudNotificationUseCase(notificationTitle = notificationTitle)
        }
    }

    fun initLiveUpdateNotification(
        notificationTitle: String = "Task Cloud"
    ) {
        viewModelScope.launch {
            pushTaskCloudNotificationUseCase(notificationTitle = notificationTitle)
        }
    }
}
