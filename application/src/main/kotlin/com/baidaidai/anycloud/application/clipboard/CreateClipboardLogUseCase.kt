package com.baidaidai.anycloud.application.clipboard

import com.baidaidai.anycloud.data.clipboard.repository.ClipboardLogRepositoryImpl
import com.baidaidai.anycloud.domain.clipboard.ClipboardLog
import javax.inject.Inject

class CreateClipboardLogUseCase @Inject constructor(
    private val clipboardLogRepositoryImpl: ClipboardLogRepositoryImpl
) {

    suspend operator fun invoke(
        clipboardLog: ClipboardLog
    ): ClipboardLog {
        val currentUnixTime = System.currentTimeMillis()
        val copiedClipboardLog = clipboardLog.copy(
            unixTime = currentUnixTime
        )

        clipboardLogRepositoryImpl.createClipboardLog(copiedClipboardLog)

        return copiedClipboardLog
    }
}