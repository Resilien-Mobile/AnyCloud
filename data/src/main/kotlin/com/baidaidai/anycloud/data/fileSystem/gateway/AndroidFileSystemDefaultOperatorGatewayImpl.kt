package com.baidaidai.anycloud.data.fileSystem.gateway

import android.content.Context
import com.baidaidai.anycloud.domain.fileSystem.gateway.AndroidFileSystemDefaultOperatorGateway
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import javax.inject.Inject

class AndroidFileSystemDefaultOperatorGatewayImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : AndroidFileSystemDefaultOperatorGateway {

    override fun findExternalAppFilesDirectoryFile(): File? {
        return context.getExternalFilesDir(null)
    }

}
