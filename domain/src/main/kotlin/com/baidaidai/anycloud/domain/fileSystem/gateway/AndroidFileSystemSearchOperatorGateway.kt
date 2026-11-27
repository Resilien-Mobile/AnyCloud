package com.baidaidai.anycloud.domain.fileSystem.gateway

import java.io.File

/**
 * 裁剪移植自 Rootless Store 的同名 Gateway，仅保留本地助手需要的查找能力。
 */
interface AndroidFileSystemSearchOperatorGateway {

    fun findRegularFile(
        file: File
    ): File?

}
