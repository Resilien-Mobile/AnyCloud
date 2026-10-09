package com.baidaidai.anycloud.data.shizuku.server

import android.content.ClipData
import android.content.Context
import android.os.IBinder
import android.util.Log

class ClipboardUserService(
    private val context: Context
) : IClipBoardUserService.Stub() {

    override fun getClipBoardValue(): String? {
        val clipData = getPrimaryClipByIClipboard() ?: return null

        if (clipData.itemCount == 0) return null

        val clipBoardValue = clipData
            .getItemAt(0)
            .coerceToText(context)
            ?.toString()

        return if (clipBoardValue.isNullOrEmpty()) null else clipBoardValue
    }

    private fun getPrimaryClipByIClipboard(): ClipData? {
        return runCatching {
            val clipboardBinder = getSystemServiceBinder(Context.CLIPBOARD_SERVICE)
            val clipboardService = asIClipboard(clipboardBinder)
            val getPrimaryClipMethod = clipboardService.javaClass.getMethod(
                "getPrimaryClip",
                String::class.java,
                String::class.java,
                Int::class.javaPrimitiveType,
                Int::class.javaPrimitiveType
            )

            getPrimaryClipMethod.invoke(
                clipboardService,
                shellPackageName,
                null,
                resolveUserId(),
                Context.DEVICE_ID_DEFAULT
            ) as? ClipData
        }.getOrElse { throwable ->
            Log.e(logTag, "read primaryClip by IClipboard failed", throwable)
            null
        }
    }

    private fun getSystemServiceBinder(serviceName: String): IBinder {
        val serviceManagerClass = Class.forName("android.os.ServiceManager")
        val getServiceMethod = serviceManagerClass.getDeclaredMethod(
            "getService",
            String::class.java
        )

        return getServiceMethod.invoke(null, serviceName) as IBinder
    }

    private fun asIClipboard(binder: IBinder): Any {
        val clipboardStubClass = Class.forName("android.content.IClipboard\$Stub")
        val asInterfaceMethod = clipboardStubClass.getDeclaredMethod(
            "asInterface",
            IBinder::class.java
        )

        return requireNotNull(asInterfaceMethod.invoke(null, binder)) {
            "IClipboard service is unavailable"
        }
    }

    private fun resolveUserId(): Int {
        return android.os.Process.myUid() / perUserRange
    }

    private companion object {
        const val logTag = "ClipboardUserService"
        const val shellPackageName = "com.android.shell"
        const val perUserRange = 100000
    }
}
