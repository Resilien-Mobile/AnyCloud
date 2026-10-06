package com.baidaidai.anycloud.application.clipboard

import com.baidaidai.anycloud.data.clipboard.repository.ClipboardPolicyRepositoryImpl
import com.baidaidai.anycloud.domain.clipboard.ClipboardPolicy
import javax.inject.Inject

class CreateClipboardPolicyUseCase @Inject constructor(
    private val clipboardPolicyRepositoryImpl: ClipboardPolicyRepositoryImpl
) {

    suspend operator fun invoke(
        clipboardPolicy: ClipboardPolicy
    ): ClipboardPolicy {
        val currentUnixTimeStamp = System.currentTimeMillis()
        val copiedClipboardPolicy = clipboardPolicy.copy(
            unixTimeStamp = currentUnixTimeStamp
        )

        clipboardPolicyRepositoryImpl.createClipboardPolicy(copiedClipboardPolicy)

        return copiedClipboardPolicy
    }
}