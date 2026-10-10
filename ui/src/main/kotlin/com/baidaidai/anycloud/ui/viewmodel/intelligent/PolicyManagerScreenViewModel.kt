package com.baidaidai.anycloud.ui.viewmodel.intelligent

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.baidaidai.anycloud.application.clipboard.DeleteClipboardPolicyUseCase
import com.baidaidai.anycloud.application.clipboard.CreateClipboardPolicyUseCase
import com.baidaidai.anycloud.application.clipboard.ObserveClipboardPoliciesUseCase
import com.baidaidai.anycloud.domain.clipboard.ClipboardPolicy
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PolicyManagerScreenViewModel @Inject constructor(
    private val createClipboardPolicyUseCase: CreateClipboardPolicyUseCase,
    private val deleteClipboardPolicyUseCase: DeleteClipboardPolicyUseCase,
    observeClipboardPoliciesUseCase: ObserveClipboardPoliciesUseCase
) : ViewModel() {

    val clipboardPolicyList = observeClipboardPoliciesUseCase()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )

    fun createClipboardPolicy(
        clipboardPolicy: ClipboardPolicy
    ) {
        viewModelScope.launch {
            createClipboardPolicyUseCase(clipboardPolicy)
        }
    }

    fun deleteClipboardPolicy(
        clipboardPolicy: ClipboardPolicy
    ) {
        viewModelScope.launch {
            deleteClipboardPolicyUseCase(clipboardPolicy)
        }
    }
}
