package com.baidaidai.anycloud.application.clipboard

import com.baidaidai.anycloud.data.clipboard.repository.ClipboardPolicyRepositoryImpl
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveTotalClipboardPolicyCountUseCase @Inject constructor(
    private val clipboardPolicyRepositoryImpl: ClipboardPolicyRepositoryImpl
) {
    operator fun invoke(): Flow<Int> {
        val totalPolicyCountFlow = clipboardPolicyRepositoryImpl.observeTotalPolicyCount()

        return totalPolicyCountFlow
    }
}
