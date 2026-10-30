package com.baidaidai.anycloud.ui.viewmodel.intelligent

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.baidaidai.anycloud.application.clipboard.AddClipboardPolicyBundleUseCase
import com.baidaidai.anycloud.application.clipboard.CreateClipboardPolicyUseCase
import com.baidaidai.anycloud.application.clipboard.DeleteClipboardPolicyUseCase
import com.baidaidai.anycloud.application.clipboard.ObserveClipboardPoliciesUseCase
import com.baidaidai.anycloud.domain.clipboard.ClipboardError
import com.baidaidai.anycloud.domain.clipboard.ClipboardPolicy
import com.github.michaelbull.result.onErr
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

data class PolicyManagerScreenUiState(
    val currentSelectedIndex: Int = 0
)

@HiltViewModel
class PolicyManagerScreenViewModel @Inject constructor(
    private val addClipboardPolicyBundleUseCase: AddClipboardPolicyBundleUseCase,
    private val createClipboardPolicyUseCase: CreateClipboardPolicyUseCase,
    private val deleteClipboardPolicyUseCase: DeleteClipboardPolicyUseCase,
    observeClipboardPoliciesUseCase: ObserveClipboardPoliciesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(PolicyManagerScreenUiState())
    val uiState: StateFlow<PolicyManagerScreenUiState> = _uiState.asStateFlow()

    private val _clipboardPolicyBundleError = MutableSharedFlow<ClipboardError?>()
    val clipboardPolicyBundleError: SharedFlow<ClipboardError?> = _clipboardPolicyBundleError.asSharedFlow()

    val clipboardPolicyList = observeClipboardPoliciesUseCase()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )

    fun addClipboardPolicyBundle(
        uri: String
    ) {
        viewModelScope.launch {
            addClipboardPolicyBundleUseCase(uri)
                .onErr { clipboardError ->
                    _clipboardPolicyBundleError.emit(clipboardError)
                }
        }
    }

    fun dismissClipboardPolicyBundleError() {
        viewModelScope.launch {
            _clipboardPolicyBundleError.emit(null)
        }
    }

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
