package com.baidaidai.anycloud.application.clipboard

import com.baidaidai.anycloud.data.clipboard.repository.ClipboardGroupRepositoryImpl
import com.baidaidai.anycloud.domain.clipboard.ClipboardGroup
import javax.inject.Inject

class CreateClipboardGroupUseCase @Inject constructor(
    private val clipboardGroupRepositoryImpl: ClipboardGroupRepositoryImpl
) {

    suspend operator fun invoke(
        clipboardGroup: ClipboardGroup
    ): ClipboardGroup {
        val currentUnixTimeStamp = System.currentTimeMillis()
        val copiedClipboardGroup = clipboardGroup.copy(
            unixTimeStamp = currentUnixTimeStamp
        )

        clipboardGroupRepositoryImpl.createClipboardGroup(copiedClipboardGroup)

        return copiedClipboardGroup
    }
}
