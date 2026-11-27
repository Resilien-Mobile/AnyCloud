package com.baidaidai.anycloud.data.intelligentlife.geteway

import android.util.Log
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import com.github.michaelbull.result.getOrElse
import dagger.hilt.android.EntryPointAccessors
import java.io.File
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class IntelligentLifeGatewayInstrumentedTest {

    @Test
    fun extractsDrinkDetailsFromOcrText() {
        val ocrText = """
            等待取餐
            已制作完成，请前往自提点凭取餈码取餐。
            168
            +幸运取餐码+
            幸运一路发生
            + 请凭幸运码取餐+
            找好友取餐
            ＋分享好运气
            瑞源名嘉店（NO.13940）
            黄岛区庐山路8号瑞源名嘉国际底商
            1导航＞
            冰吸生椰拿铁（首创）
            咖啡卡劵
            大杯/冰/意式拼配/默认浓度/不另外加糖/无奶油
            保存为默认口味，下单更方便
            ·0
            x1
            （7保存
            确认取餐
        """.trimIndent()

        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val modelFile = File(context.getExternalFilesDir(null), "models/Qwen.litertlm")
        Log.i("IntelligentLifeGatewayTest", "模型文件: ${modelFile.absolutePath}, 大小: ${modelFile.length()} bytes")
        val dependencies = EntryPointAccessors.fromApplication(
            context,
            IntelligentLifeTestEntryPoint::class.java
        )

        // CPU is the default for repeatable device tests; pass -e backend auto to use the app's selection.
        if (InstrumentationRegistry.getArguments().getString("backend", "cpu") == "cpu") {
            IntelligentLifeTestAccess.useCpu(dependencies.assistantApi(), context).getOrElse { error ->
                throw AssertionError("CPU 模型初始化失败: $error")
            }
        }

        val dto = IntelligentLifeTestAccess.resolveMaskedDto(dependencies.gateway(), ocrText).getOrElse { error ->
            throw AssertionError("模型推理失败: $error")
        }
        Log.i("IntelligentLifeGatewayTest", "模型返回: $dto")

        assertTrue("品牌应为咖啡或 null，实际为: ${dto.brand}", dto.brand == "咖啡" || dto.brand == null)
        assertEquals("取餐码", "168", dto.number)
        assertTrue("content 应包含冰吸生椰拿铁，实际为: ${dto.content}", dto.content?.contains("冰吸生椰拿铁") == true)
    }
}
