package com.baidaidai.anycloud.application.clipboard

import com.baidaidai.anycloud.data.clipboard.repository.ClipboardGroupRepositoryImpl
import com.baidaidai.anycloud.domain.clipboard.ClipboardGroup
import javax.inject.Inject

class DeleteClipboardGroupUseCase @Inject constructor(
    private val clipboardGroupRepositoryImpl: ClipboardGroupRepositoryImpl
) {
    suspend operator fun invoke(
        clipboardGroup: ClipboardGroup
    ) {
        clipboardGroupRepositoryImpl.deleteClipboardGroup(clipboardGroup)
    }
}