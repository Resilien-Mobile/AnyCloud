package com.baidaidai.anycloud.application.setting

import com.baidaidai.anycloud.data.setting.repository.SettingRepositoryImpl
import javax.inject.Inject

class SyncOngoingStyleEnabledUseCase @Inject constructor(
    private val settingRepositoryImpl: SettingRepositoryImpl
) {
    operator fun invoke(
        isEnabled: Boolean
    ) {
        settingRepositoryImpl.syncOngoingStyleEnabled(
            isEnabled = isEnabled
        )
    }
}
