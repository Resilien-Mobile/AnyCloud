package com.baidaidai.anycloud.ui.viewmodel.intelligent

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.baidaidai.anycloud.application.clipboard.ObserveClipboardGroupsUseCase
import com.baidaidai.anycloud.application.clipboard.ObserveClipboardLogsUseCase
import com.baidaidai.anycloud.application.clipboard.ObserveTotalClipboardPolicyCountUseCase
import com.baidaidai.anycloud.application.setting.ObserveClipboardListeningEnabledUseCase
import com.baidaidai.anycloud.application.setting.SyncClipboardListeningEnabledUseCase
import com.baidaidai.anycloud.application.shizuku.ObserveShizukuStatusUseCase
import com.baidaidai.anycloud.domain.clipboard.ClipboardLog
import com.baidaidai.anycloud.domain.shizuku.ShizukuStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

// 与 ClipboardLogRepositoryImpl 的 50 条上限保持一致
private val placeholderClipboardLogList: List<ClipboardLog?> = List(50) { null }

@HiltViewModel
class ClipBoardPilotScreenViewModel @Inject constructor(
    observeShizukuStatusUseCase: ObserveShizukuStatusUseCase,
    observeClipboardListeningEnabledUseCase: ObserveClipboardListeningEnabledUseCase,
    observeClipboardGroupsUseCase: ObserveClipboardGroupsUseCase,
    observeClipboardLogsUseCase: ObserveClipboardLogsUseCase,
    observeTotalClipboardPolicyCountUseCase: ObserveTotalClipboardPolicyCountUseCase,
    private val syncClipboardListeningEnabledUseCase: SyncClipboardListeningEnabledUseCase
) : ViewModel() {

    val shizukuStatus = observeShizukuStatusUseCase().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = ShizukuStatus.UNAVAILABLE
    )

    val isClipboardListeningEnabled: StateFlow<Boolean> =
        observeClipboardListeningEnabledUseCase().stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = false
        )

    val clipboardGroupCount: StateFlow<Int> =
        observeClipboardGroupsUseCase()
            .map { clipboardGroupList ->
                clipboardGroupList.size
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = 0
            )

    val totalClipboardPolicyCount: StateFlow<Int> =
        observeTotalClipboardPolicyCountUseCase().stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = 0
        )

    val clipboardLogList: StateFlow<List<ClipboardLog?>> =
        observeClipboardLogsUseCase()
            .map { clipboardLogList ->
                // 空态在 VM 层拦住，下发 50 条占位空行，UI 不出现空骨架
                if (clipboardLogList.isEmpty()) {
                    placeholderClipboardLogList
                } else {
                    clipboardLogList
                }
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = placeholderClipboardLogList
            )

    fun syncClipboardListeningEnabled(
        isEnabled: Boolean
    ) {
        syncClipboardListeningEnabledUseCase(
            isEnabled = isEnabled
        )
    }
}
