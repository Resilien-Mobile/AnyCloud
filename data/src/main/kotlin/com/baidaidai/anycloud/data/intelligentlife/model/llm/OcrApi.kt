package com.baidaidai.anycloud.data.intelligentlife.model.llm

import android.graphics.Bitmap

interface OcrApi {

    fun startOcrService()

    suspend fun resolveOcrContent(
        imageBitmap: Bitmap
    ): String

    fun stopOcrService()


}