package com.baidaidai.anycloud.application.clipboard

import com.baidaidai.anycloud.data.clipboard.repository.ClipboardPolicyRepositoryImpl
import com.baidaidai.anycloud.domain.clipboard.ClipboardPolicy
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveClipboardPoliciesUseCase @Inject constructor(
    private val clipboardPolicyRepositoryImpl: ClipboardPolicyRepositoryImpl
) {
    operator fun invoke(): Flow<List<ClipboardPolicy>> {
        val clipboardPolicyFlow = clipboardPolicyRepositoryImpl.observeClipboardPolicies()

        return clipboardPolicyFlow
    }
}
