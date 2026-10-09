package com.baidaidai.anycloud.application.clipboard

import com.baidaidai.anycloud.data.clipboard.repository.ClipboardLogRepositoryImpl
import com.baidaidai.anycloud.domain.clipboard.ClipboardLog
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveClipboardLogsUseCase @Inject constructor(
    private val clipboardLogRepositoryImpl: ClipboardLogRepositoryImpl
) {
    operator fun invoke(): Flow<List<ClipboardLog>> {
        val clipboardLogFlow = clipboardLogRepositoryImpl.observeClipboardLogs()

        return clipboardLogFlow
    }
}