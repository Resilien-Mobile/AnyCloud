package com.baidaidai.anycloud.ui.viewmodel.intelligent

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.baidaidai.anycloud.application.shizuku.EnsureShizukuPermissionUseCase
import com.baidaidai.anycloud.application.shizuku.StartShizukuUserServiceUseCase
import com.baidaidai.anycloud.ui.R
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShizukuAdbScreenViewModel @Inject constructor(
    @param:ApplicationContext private val context: Context,
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
                            errorMessage = context.getString(R.string.shizuku_screen_error_permission_not_granted),
                            errorCause = context.getString(R.string.shizuku_screen_error_permission_not_granted_cause)
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
                            errorMessage = context.getString(R.string.shizuku_screen_error_user_service_not_started),
                            errorCause = context.getString(R.string.shizuku_screen_error_user_service_not_started_cause)
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
            errorMessage = message ?: context.getString(R.string.shizuku_screen_error_unknown),
            errorCause = stackTraceToString()
        )

        return error
    }
}

data class ShizukuAdbScreenError(
    val errorMessage: String,
    val errorCause: String
)
