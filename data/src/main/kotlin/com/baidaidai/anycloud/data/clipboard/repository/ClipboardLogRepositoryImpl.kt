package com.baidaidai.anycloud.data.clipboard.repository

import com.baidaidai.anycloud.data.clipboard.mapper.ClipboardLogMapper.toClipboardLog
import com.baidaidai.anycloud.data.clipboard.mapper.ClipboardLogMapper.toLogEntity
import com.baidaidai.anycloud.data.database.AnyCloudDataBase
import com.baidaidai.anycloud.domain.clipboard.ClipboardLog
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ClipboardLogRepositoryImpl @Inject constructor(
    private val anyCloudDataBase: AnyCloudDataBase
) {
    private val logDao = anyCloudDataBase.logDao()
    private val maxLogCount = 50

    /**
     * CRUD
     */

    // Create
    suspend fun createClipboardLog(
        clipboardLog: ClipboardLog
    ) {
        val logEntityCount = logDao.getLogEntityCount()

        if (logEntityCount >= maxLogCount) {
            logDao.deleteLastLogEntity()
        }

        val logEntity = clipboardLog.toLogEntity()

        logDao.insertLogEntity(logEntity)
    }

    // Update

    // Read
    fun observeClipboardLogs(): Flow<List<ClipboardLog>> {
        val logEntityFlow = logDao.observeLogEntities()

        val clipboardLogFlow = logEntityFlow.map { logEntityList ->
            logEntityList.map { logEntity ->
                logEntity.toClipboardLog()
            }
        }

        return clipboardLogFlow
    }

    // Delete
}