package com.baidaidai.anycloud.data.clipboard.mapper

import com.baidaidai.anycloud.data.clipboard.database.LogEntity
import com.baidaidai.anycloud.domain.clipboard.ClipboardLog

object ClipboardLogMapper {

    internal fun LogEntity.toClipboardLog(): ClipboardLog {
        val clipboardLog = ClipboardLog(
            unixTime = unixTime,
            host = host,
            isMatchedResult = isMatchedResult
        )

        return clipboardLog
    }

    internal fun ClipboardLog.toLogEntity(): LogEntity {
        val logEntity = LogEntity(
            unixTime = unixTime,
            host = host,
            isMatchedResult = isMatchedResult
        )

        return logEntity
    }
}