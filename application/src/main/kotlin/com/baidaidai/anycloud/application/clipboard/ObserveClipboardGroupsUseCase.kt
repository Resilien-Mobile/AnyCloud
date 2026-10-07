package com.baidaidai.anycloud.application.clipboard

import com.baidaidai.anycloud.data.clipboard.repository.ClipboardGroupRepositoryImpl
import com.baidaidai.anycloud.domain.clipboard.ClipboardGroup
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveClipboardGroupsUseCase @Inject constructor(
    private val clipboardGroupRepositoryImpl: ClipboardGroupRepositoryImpl
) {
    operator fun invoke(): Flow<List<ClipboardGroup>> {
        val clipboardGroupFlow = clipboardGroupRepositoryImpl.observeClipboardGroups()

        return clipboardGroupFlow
    }
}
