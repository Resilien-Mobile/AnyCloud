package com.baidaidai.anycloud.ui.viewmodel.intelligent

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.baidaidai.anycloud.application.clipboard.DeleteClipboardPolicyUseCase
import com.baidaidai.anycloud.application.clipboard.CreateClipboardPolicyUseCase
import com.baidaidai.anycloud.application.clipboard.ObserveClipboardPoliciesUseCase
import com.baidaidai.anycloud.domain.clipboard.ClipboardPolicy
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

data class PolicyManagerScreenUiState(
    val currentSelectedIndex: Int = 0
)

@HiltViewModel
class PolicyManagerScreenViewModel @Inject constructor(
    private val createClipboardPolicyUseCase: CreateClipboardPolicyUseCase,
    private val deleteClipboardPolicyUseCase: DeleteClipboardPolicyUseCase,
    observeClipboardPoliciesUseCase: ObserveClipboardPoliciesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(PolicyManagerScreenUiState())
    val uiState: StateFlow<PolicyManagerScreenUiState> = _uiState.asStateFlow()

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

    fun updateCurrentSelectedIndex(
        index: Int
    ) {
        _uiState.value = _uiState.value.copy(
            currentSelectedIndex = index
        )
    }
}
