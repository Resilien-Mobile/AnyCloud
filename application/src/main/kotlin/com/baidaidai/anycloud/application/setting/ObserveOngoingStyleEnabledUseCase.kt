package com.baidaidai.anycloud.application.setting

import com.baidaidai.anycloud.data.setting.repository.SettingRepositoryImpl
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveOngoingStyleEnabledUseCase @Inject constructor(
    private val settingRepositoryImpl: SettingRepositoryImpl
) {
    operator fun invoke(): Flow<Boolean> {
        val isOngoingStyleEnabledFlow = settingRepositoryImpl.observeOngoingStyleEnabled()

        return isOngoingStyleEnabledFlow
    }
}
