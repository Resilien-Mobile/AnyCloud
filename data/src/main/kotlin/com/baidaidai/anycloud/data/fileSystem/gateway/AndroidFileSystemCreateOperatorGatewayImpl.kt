package com.baidaidai.anycloud.data.fileSystem.gateway

import com.baidaidai.anycloud.domain.fileSystem.gateway.AndroidFileSystemCreateOperatorGateway
import java.io.File
import javax.inject.Inject

class AndroidFileSystemCreateOperatorGatewayImpl @Inject constructor() :
    AndroidFileSystemCreateOperatorGateway {

    override fun resolveChildFile(
        parentDirectory: File,
        childName: String
    ): File {
        return File(parentDirectory, childName)
    }

    override fun ensureDirectory(
        directory: File
    ): File? {
        return directory.takeIf {
            it.isDirectory || it.mkdirs()
        }
    }

}
