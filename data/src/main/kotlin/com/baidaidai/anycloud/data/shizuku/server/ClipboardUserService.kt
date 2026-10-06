package com.baidaidai.anycloud.data.shizuku.server

import android.content.ClipboardManager
import android.content.Context

class ClipboardUserService(
    private val context: Context
) : IClipBoardUserService.Stub() {

    override fun getClipBoardValue(): String? {
        val clipboardManager = context.getSystemService(ClipboardManager::class.java)
        val clipData = clipboardManager.primaryClip ?: return null
        if (clipData.itemCount == 0) return null

        val clipBoardValue = clipData
            .getItemAt(0)
            .coerceToText(context)
            ?.toString()

        return if (clipBoardValue.isNullOrEmpty()) null else clipBoardValue
    }

}
