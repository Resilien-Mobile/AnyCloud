package com.baidaidai.anycloud.ui.viewmodel.intelligent

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.baidaidai.anycloud.application.clipboard.CreateClipboardGroupUseCase
import com.baidaidai.anycloud.application.clipboard.DeleteClipboardGroupUseCase
import com.baidaidai.anycloud.application.clipboard.ObserveClipboardGroupsUseCase
import com.baidaidai.anycloud.domain.clipboard.ClipboardGroup
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PolicyGroupScreenViewModel @Inject constructor(
    private val createClipboardGroupUseCase: CreateClipboardGroupUseCase,
    private val deleteClipboardGroupUseCase: DeleteClipboardGroupUseCase,
    observeClipboardGroupsUseCase: ObserveClipboardGroupsUseCase
) : ViewModel() {

    val clipboardGroupList = observeClipboardGroupsUseCase()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )

    fun createClipboardGroup(
        clipboardGroup: ClipboardGroup
    ) {
        viewModelScope.launch {
            createClipboardGroupUseCase(clipboardGroup)
        }
    }

    fun deleteClipboardGroup(
        clipboardGroup: ClipboardGroup
    ) {
        viewModelScope.launch {
            deleteClipboardGroupUseCase(clipboardGroup)
        }
    }
}
