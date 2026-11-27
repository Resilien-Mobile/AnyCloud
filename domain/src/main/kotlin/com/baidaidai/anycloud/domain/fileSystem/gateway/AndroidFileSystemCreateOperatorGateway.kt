package com.baidaidai.anycloud.domain.fileSystem.gateway

import java.io.File

/**
 * 裁剪移植自 Rootless Store 的同名 Gateway，仅保留本地助手需要的创建能力。
 */
interface AndroidFileSystemCreateOperatorGateway {

    fun resolveChildFile(
        parentDirectory: File,
        childName: String
    ): File

    fun ensureDirectory(
        directory: File
    ): File?

}
