package com.baidaidai.anycloud.domain.notification.error

import com.baidaidai.anycloud.domain.error.AnyCloudError

data class NotificationError(
    override val errorMessage: String,
    override val errorCause: String,
    override val errorCompanion: String? = null
) : AnyCloudError