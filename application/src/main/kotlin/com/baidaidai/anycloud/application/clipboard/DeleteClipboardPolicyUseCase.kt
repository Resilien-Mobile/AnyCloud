package com.baidaidai.anycloud.application.clipboard

import com.baidaidai.anycloud.data.clipboard.repository.ClipboardPolicyRepositoryImpl
import com.baidaidai.anycloud.domain.clipboard.ClipboardPolicy
import javax.inject.Inject

class DeleteClipboardPolicyUseCase @Inject constructor(
    private val clipboardPolicyRepositoryImpl: ClipboardPolicyRepositoryImpl
) {
    suspend operator fun invoke(
        clipboardPolicy: ClipboardPolicy
    ) {
        clipboardPolicyRepositoryImpl.deleteClipboardPolicy(clipboardPolicy)
    }
}