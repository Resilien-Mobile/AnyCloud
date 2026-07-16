package com.baidaidai.anycloud.ui.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.baidaidai.anycloud.application.notification.AddOneNotificationConfigUseCase
import com.baidaidai.anycloud.application.notification.DeleteOneNotificationConfigUseCase
import com.baidaidai.anycloud.application.notification.GetAllNotificationConfigUseCase
import com.baidaidai.anycloud.domain.notification.model.NotificationConfig
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val addOneNotificationConfigUseCase: AddOneNotificationConfigUseCase,
    private val deleteOneNotificationConfigUseCase: DeleteOneNotificationConfigUseCase,
    private val getAllNotificationConfigUseCase: GetAllNotificationConfigUseCase
) : ViewModel() {

    val notificationConfigList = getAllNotificationConfigUseCase().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = emptyList()
    )

    // CUD

    // Create
    fun createOneNotificationConfig(
        notificationTitle: String = "Any Cloud",
        notificationContent: String,
        notificationID: Int = 1001
    ) {
        viewModelScope.launch {
            addOneNotificationConfigUseCase(
                notificationTitle = notificationTitle,
                notificationContent = notificationContent,
                notificationID = notificationID
            )
        }
    }
    // Update
    //Delete
    fun deleteOneNotificationConfig(
        notificationConfig: NotificationConfig
    ) {
        viewModelScope.launch {
            deleteOneNotificationConfigUseCase(notificationConfig)
        }
    }

}
