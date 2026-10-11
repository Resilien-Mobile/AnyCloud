package com.baidaidai.anycloud.ui.viewmodel.things

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.baidaidai.anycloud.application.notification.ongoing.PushOneOngoingNotificationConfigUseCase
import com.baidaidai.anycloud.application.notification.ongoing.DeleteOneOngoingNotificationConfigUseCase
import com.baidaidai.anycloud.application.notification.ongoing.GetAllOngoingNotificationConfigUseCase
import com.baidaidai.anycloud.application.notification.ongoing.UpdateOneOngoingNotificationTaskFinishedUseCase
import com.baidaidai.anycloud.domain.notification.model.NotificationConfig
import com.baidaidai.anycloud.ui.R
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    @param:ApplicationContext private val context: Context,
    private val pushOneOngoingNotificationConfigUseCase: PushOneOngoingNotificationConfigUseCase,
    private val deleteOneOngoingNotificationConfigUseCase: DeleteOneOngoingNotificationConfigUseCase,
    private val getAllOnGoingNotificationConfigUseCase: GetAllOngoingNotificationConfigUseCase,
    private val updateOneOngoingNotificationTaskFinishedUseCase: UpdateOneOngoingNotificationTaskFinishedUseCase
) : ViewModel() {

    val notificationConfigList = getAllOnGoingNotificationConfigUseCase().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = emptyList()
    )

    // CUD

    // Create
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
