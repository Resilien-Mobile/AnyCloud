package com.baidaidai.anycloud.data.fileSystem.gateway

import com.baidaidai.anycloud.domain.fileSystem.gateway.AndroidFileSystemSearchOperatorGateway
import java.io.File
import javax.inject.Inject

class AndroidFileSystemSearchOperatorGatewayImpl @Inject constructor() :
    AndroidFileSystemSearchOperatorGateway {

    override fun findRegularFile(
        file: File
    ): File? {
        return file.takeIf { it.isFile }
    }

}
