package com.baidaidai.anycloud

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import com.baidaidai.anycloud.application.intelligentlife.SyncIntelligentPromptUseCase
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

/**
 * 分享面板入口 + 处理器（原 IntelligentLifeAssistantService 已并入本 Activity）：
 * 接收 SEND/VIEW 的图片，解码后 dispatch 给 [SyncIntelligentPromptUseCase]，
 * 处理完成后再 finish；透明无 UI，不进最近任务。
 */
@AndroidEntryPoint
class IntelligentLifeShareActivity : ComponentActivity() {

    @Inject
    lateinit var syncIntelligentPromptUseCase: SyncIntelligentPromptUseCase

    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        val intent = getIntent()
        val imageUri = resolveImageUri(intent)

        if (imageUri == null) {
            finish()
            return
        }

        coroutineScope.launch {

            try {

                // 上传的图片：解码后直接 dispatch 给 use case
                val bitmap = decodeImage(imageUri)
                if (bitmap != null) {
                    syncIntelligentPromptUseCase(
                        inputPicture = bitmap
                    )
                }

            } finally {

                runOnUiThread { finish() }

            }

        }
    }

    override fun onDestroy() {
        coroutineScope.cancel()
        super.onDestroy()
    }

    private fun resolveImageUri(intent: Intent?): Uri? =
        when (intent?.action) {
            Intent.ACTION_SEND -> {
                intent.getParcelableExtra(
                    Intent.EXTRA_STREAM,
                    Uri::class.java
                )
            }
            Intent.ACTION_VIEW -> {
                intent.data
            }
            else -> null
        }

    private fun decodeImage(
        uri: Uri
    ): Bitmap? {

        val resolver = contentResolver

        val bounds = BitmapFactory.Options().apply {
            inJustDecodeBounds = true
        }
        resolver
            .openInputStream(uri)
            ?.use { inputStream ->
                BitmapFactory.decodeStream(
                    inputStream,
                    null,
                    bounds
                )
            }
        if (bounds.outWidth <= 0 || bounds.outHeight <= 0) {
            return null
        }

        return resolver
            .openInputStream(uri)
            ?.use { inputStream ->
                BitmapFactory.decodeStream(inputStream)
            }

    }

}
