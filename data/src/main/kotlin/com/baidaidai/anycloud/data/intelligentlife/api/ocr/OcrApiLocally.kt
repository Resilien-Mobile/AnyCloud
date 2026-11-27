package com.baidaidai.anycloud.data.intelligentlife.api.ocr

import android.graphics.Bitmap
import android.util.Log
import com.baidaidai.anycloud.data.intelligentlife.model.llm.OcrApi
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.TextRecognizer
import com.google.mlkit.vision.text.chinese.ChineseTextRecognizerOptions
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlinx.coroutines.suspendCancellableCoroutine

@Singleton
class OcrApiLocally @Inject constructor(
) : OcrApi {

    /**
     * MVP 阶段使用 Google ML Kit 中文文字识别顶上；
     * 后续 PP-OCR 方案就绪后，由上层注入自 patch 的 adapter 替换本实现。
     */
    private val textRecognizer: TextRecognizer by lazy {
        TextRecognition.getClient(
            ChineseTextRecognizerOptions
                .Builder()
                .build()
        )
    }

    override fun startOcrService() {
        TODO("Not yet implemented")
    }

    override suspend fun resolveOcrContent(
        imageBitmap: Bitmap
    ): String =
        suspendCancellableCoroutine { continuation ->

            val inputImage = InputImage.fromBitmap(
                ensureArgb8888(imageBitmap),
                0
            )

            textRecognizer
                .process(inputImage)
                .addOnSuccessListener { visionText ->

                    if (continuation.isActive) {
                        Log.d(
                            "OcrApiLocally",
                            "OCR 识别原文: ${visionText.text}"
                        )
                        continuation.resume(visionText.text)
                    }
                }
                .addOnFailureListener { exception ->
                    if (continuation.isActive) {
                        continuation.resumeWithException(
                            IOException(
                                "ML Kit OCR failed",
                                exception
                            )
                        )
                    }
                }
                .addOnCanceledListener {
                    if (continuation.isActive) {
                        continuation.resumeWithException(
                            IOException("ML Kit OCR task was cancelled")
                        )
                    }
                }

        }

    override fun stopOcrService() {
        TODO("Not yet implemented")
    }

    /**
     * ML Kit 的 [InputImage.fromBitmap] 要求 ARGB_8888，
     * 屏幕截图等来源可能是 RGB_565/HARDWARE，先转一次。
     */
    private fun ensureArgb8888(bitmap: Bitmap): Bitmap =
        if (bitmap.config == Bitmap.Config.ARGB_8888) {
            bitmap
        } else {
            bitmap.copy(Bitmap.Config.ARGB_8888, false)
        }

}
