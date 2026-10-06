package com.baidaidai.anycloud.ui.viewmodel.intelligent

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.baidaidai.anycloud.application.shizuku.EnsureShizukuPermissionUseCase
import com.baidaidai.anycloud.application.shizuku.StartShizukuUserServiceUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShizukuAdbScreenViewModel @Inject constructor(
    private val ensureShizukuPermissionUseCase: EnsureShizukuPermissionUseCase,
    private val startShizukuUserServiceUseCase: StartShizukuUserServiceUseCase
) : ViewModel() {

    private val _isShizukuActive = MutableStateFlow(false)
    val isShizukuActive = _isShizukuActive.asStateFlow()

    private val _isUserServiceActive = MutableStateFlow(false)
    val isUserServiceActive = _isUserServiceActive.asStateFlow()

    private val _shizukuError = MutableSharedFlow<ShizukuAdbScreenError?>()
    val shizukuError = _shizukuError.asSharedFlow()

    fun dismissShizukuError() {
        viewModelScope.launch {
            _shizukuError.emit(null)
        }
    }

    fun ensureShizukuPermission() {
        viewModelScope.launch {
            val result = ensureShizukuPermissionUseCase()

            result.onSuccess { isShizukuActive ->
                _isShizukuActive.value = isShizukuActive

                if (!isShizukuActive) {
                    _shizukuError.emit(
                        ShizukuAdbScreenError(
                            errorMessage = "Shizuku permission is not granted",
                            errorCause = "Please check Shizuku installation, running state, and authorization status."
                        )
                    )
                }
            }.onFailure { throwable ->
                _shizukuError.emit(throwable.toShizukuAdbScreenError())
            }
        }
    }

    fun startShizukuUserService() {
        viewModelScope.launch {
            val result = startShizukuUserServiceUseCase()

            result.onSuccess { isUserServiceActive ->
                _isUserServiceActive.value = isUserServiceActive

                if (!isUserServiceActive) {
                    _shizukuError.emit(
                        ShizukuAdbScreenError(
                            errorMessage = "Shizuku user service is not started",
                            errorCause = "Please check Shizuku installation, running state, and authorization status."
                        )
                    )
                }
            }.onFailure { throwable ->
                _shizukuError.emit(throwable.toShizukuAdbScreenError())
            }
        }
    }

    private fun Throwable.toShizukuAdbScreenError(): ShizukuAdbScreenError {
        val error = ShizukuAdbScreenError(
            errorMessage = message ?: "Unknown Shizuku error",
            errorCause = stackTraceToString()
        )

        return error
    }
}

data class ShizukuAdbScreenError(
    val errorMessage: String,
    val errorCause: String
)
