package com.baidaidai.anycloud.data.intelligentlife.api.assistant

import android.content.Context
import android.util.Log
import com.baidaidai.anycloud.core.array.formatAsMultilineString
import com.baidaidai.anycloud.data.intelligentlife.api.assistant.dto.IntelligentPromptDto
import com.baidaidai.anycloud.data.intelligentlife.model.llm.AssistantApi
import com.baidaidai.anycloud.data.intelligentlife.model.llm.IntelligentPrompts
import com.baidaidai.anycloud.domain.fileSystem.gateway.AndroidFileSystemCreateOperatorGateway
import com.baidaidai.anycloud.domain.fileSystem.gateway.AndroidFileSystemDefaultOperatorGateway
import com.baidaidai.anycloud.domain.fileSystem.gateway.AndroidFileSystemSearchOperatorGateway
import com.baidaidai.anycloud.domain.intelligentlife.error.IntelligentLifeError
import com.baidaidai.anycloud.domain.intelligentlife.model.AssistantJudgeStatus
import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import com.github.michaelbull.result.getOrElse
import com.github.michaelbull.result.mapError
import com.github.michaelbull.result.onErr
import com.github.michaelbull.result.onOk
import com.github.michaelbull.result.runCatching
import com.google.ai.edge.litertlm.Backend
import com.google.ai.edge.litertlm.Channel
import com.google.ai.edge.litertlm.ConversationConfig
import com.google.ai.edge.litertlm.Contents
import com.google.ai.edge.litertlm.Engine
import com.google.ai.edge.litertlm.EngineConfig
import com.google.ai.edge.litertlm.Message
import com.google.ai.edge.litertlm.ResponseFormat
import com.google.ai.edge.litertlm.SamplerConfig
import com.google.ai.edge.litertlm.ThinkingConfig
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.serialization.json.Json

@Singleton
class AssistantApiLocally @Inject constructor(
    @param:ApplicationContext private val context: Context,
    private val androidFileSystemDefaultOperatorGateway: AndroidFileSystemDefaultOperatorGateway,
    private val androidFileSystemCreateOperatorGateway: AndroidFileSystemCreateOperatorGateway,
    private val androidFileSystemSearchOperatorGateway: AndroidFileSystemSearchOperatorGateway,
) : AssistantApi {

    private companion object {
        private const val MODEL_FILE_NAME = "Qwen.litertlm"
        private val thinkBlockRegex = Regex("<think>[\\s\\S]*?</think>", RegexOption.DOT_MATCHES_ALL)

        fun stripThinkMarkers(text: String): String =
            text
                .replace(thinkBlockRegex, "")
                .replace("<think>", "")
                .replace("</think>", "")
                .trim()
    }

    @Volatile
    private var engine: Engine? = null

    init {
        initializeLlmEngine()
    }

    @Synchronized
    override fun resolveUnmaskedResponse(
        prompt: String
    ): Result<String, IntelligentLifeError> {

        if (prompt.isBlank()) {
            return Err(
                error = IntelligentLifeError(
                    errorMessage = "Prompt required",
                    errorCause = "",
                    errorCompanion = "Enter some text before requesting a local response."
                )
            )
        }

        if (engine == null) {
            initializeLlmEngine()
                .getOrElse { intelligentLifeError ->
                    return Err(error = intelligentLifeError)
                }
        }

        val engine = engine ?: return Err(
            error = IntelligentLifeError(
                errorMessage = "Local inference engine unavailable",
                errorCause = "",
                errorCompanion = "The local model engine could not be started."
            )
        )

        engine.createConversation(
            conversationConfig = ConversationConfig(
                systemInstruction = Contents.of(
                    text = IntelligentPrompts.UNMASKED_DEBUG_INSTRUCTION
                ),
                samplerConfig = SamplerConfig(
                    topK = 1,
                    topP = 1.0,
                    temperature = 0.0
                ),
                channels = listOf(
                    element = Channel(
                        channelName = "thought",
                        start = "<think>\n",
                        end = "\n</think>"
                    )
                ),
                thinkingConfig = ThinkingConfig(
                    enableThinking = false,
                    thinkingTokenBudget = 0
                ),
                maxOutputToken = 128,
                enableResponseFormat = false
            )
        ).use { llmConversation ->

            Log.d(
                "JSON_SCHEMA",
                Json.encodeToString(
                    value = IntelligentPromptDto.getShellCommandSchema()
                )
            )

            val conversationMessage = llmConversation.sendMessage(
                message = Message.user(prompt),
                maxOutputToken = 128,
                thinkingConfig = ThinkingConfig(
                    enableThinking = false,
                    thinkingTokenBudget = 0
                )
            )

            val rawResponseJson = stripThinkMarkers(
                text = conversationMessage.toString()
            )

            return Ok(value = rawResponseJson)
        }

    }

    @Synchronized
    override fun resolveMaskedResponse(
        prompt: String
    ): Result<IntelligentPromptDto, IntelligentLifeError> {

        if (prompt.isBlank()) {
            return Err(
                error = IntelligentLifeError(
                    errorMessage = "Prompt required",
                    errorCause = "",
                    errorCompanion = "Enter some text before requesting a local response."
                )
            )
        }

        if (engine == null) {
            initializeLlmEngine()
                .getOrElse { intelligentLifeError ->
                    return Err(error = intelligentLifeError)
                }
        }
        val engine: Engine = engine!!

        engine.createConversation(
            conversationConfig = ConversationConfig(
                systemInstruction = Contents.of(
                    text = IntelligentPrompts.SYSTEM_INSTRUCTION
                ),
                samplerConfig = SamplerConfig(
                    topK = 1,
                    topP = 1.0,
                    temperature = 0.0
                ),
                channels = listOf(
                    element = Channel(
                        channelName = "thought",
                        start = "<think>\n",
                        end = "\n</think>"
                    )
                ),
                thinkingConfig = ThinkingConfig(
                    enableThinking = false,
                    thinkingTokenBudget = 0
                ),
                maxOutputToken = 256,
                enableResponseFormat = true
            )
        ).use { llmConversation ->

            Log.d(
                "JSON_SCHEMA",
                Json.encodeToString(
                    value = IntelligentPromptDto.getShellCommandSchema()
                )
            )

            val conversationMessage = llmConversation
                .sendMessage(
                    message = Message.user(prompt),
                    maxOutputToken = 256,
                    thinkingConfig = ThinkingConfig(
                        enableThinking = false,
                        thinkingTokenBudget = 0
                    ),
                    responseFormat = ResponseFormat.json(
                        Json.encodeToString(
                            value = IntelligentPromptDto.getShellCommandSchema()
                        )
                    )
                )
            val rawResponseJson = stripThinkMarkers(  // 用于保险起见去掉 Thinking 的标志
                text = conversationMessage.toString()
            )

            Log.d("AssistantApiLocally", "原始模型输出: $rawResponseJson")

            val intelligentPromptDto = runCatching {
                Json.decodeFromString<IntelligentPromptDto>(
                    string = rawResponseJson
                )
            }.mapError { throwable ->
                IntelligentLifeError(
                    errorMessage = "Invalid model response",
                    errorCause = throwable.stackTrace.formatAsMultilineString(),
                    errorCompanion = "The local model returned a response that does not match the expected life event."
                )
            }.getOrElse { intelligentLifeError ->
                return Err(
                    error = intelligentLifeError
                )
            }

            when {
                intelligentPromptDto.intelligentJudgeStatus == AssistantJudgeStatus.ILLEGAL -> {
                    return Ok(
                        value = intelligentPromptDto.copy(
                            brand = null,
                            number = null,
                            content = null
                        )
                    )
                }
                intelligentPromptDto.brand.isNullOrBlank() &&
                    intelligentPromptDto.number.isNullOrBlank() &&
                    intelligentPromptDto.content.isNullOrBlank() -> {
                    return Ok(
                        value = IntelligentPromptDto(
                            intelligentJudgeStatus = AssistantJudgeStatus.LEGAL,
                            lifeType = intelligentPromptDto.lifeType,
                            brand = null,
                            number = null,
                            content = null
                        )
                    )
                }
                else -> {
                    return Ok(
                        value = intelligentPromptDto.copy(
                            brand = intelligentPromptDto.brand?.trim(),
                            number = intelligentPromptDto.number?.trim(),
                            content = intelligentPromptDto.content?.trim()
                        )
                    )
                }
            }
        }

    }


    @Synchronized
    fun initializeLlmEngine(): Result<Unit, IntelligentLifeError> {
        if (engine != null) {
            return Ok(value = Unit)
        }

        // 注意：模型包内嵌的聊天模板必须与运行时版本匹配。
        // - 0.18+ 运行时把消息 content 以「类型化片段」列表传给模板；
        // - 0.16.x 运行时传的是普通字符串。
        // 当前使用 litertlm-android 0.19.1 + 新格式模型包，二者匹配，无需覆盖模板。
        val modelFile = requireExternalModelFile()
            .getOrElse { intelligentLifeError ->
                return Err(
                    error = intelligentLifeError
                )
            }
        val cacheDirectory = File(context.cacheDir, "litertlm")
        check(cacheDirectory.isDirectory || cacheDirectory.mkdirs()) {
            "无法创建 LiteRT-LM 缓存目录：${cacheDirectory.absolutePath}"
        }

        initializeGpuEngine(
            modelFile = modelFile,
            cacheDirectory = cacheDirectory
        ).onOk {
            return Ok(value = Unit)
        }

        Log.w("AssistantApiLocally", "GPU 初始化失败，尝试使用 CPU 后端。")

        return initializeCpuEngine(
            modelFile = modelFile,
            cacheDirectory = cacheDirectory
        ).onOk {
            Ok(value = Unit)
        }.onErr {
            Err(error = it)
        }
    }

    internal fun initializeGpuEngine(
        modelFile: File,
        cacheDirectory: File
    ): Result<Unit, IntelligentLifeError> =
        runCatching {
            val newEngine = Engine(
                engineConfig = EngineConfig(
                    modelPath = modelFile.absolutePath,
                    backend = Backend.GPU(),
                    maxNumTokens = 2048,
                    cacheDir = cacheDirectory.absolutePath
                )
            )
            newEngine.initialize()
            engine = newEngine
        }.mapError { throwable ->
            IntelligentLifeError(
                errorMessage = "GPU inference initialization failed",
                errorCause = throwable.stackTrace.formatAsMultilineString(),
                errorCompanion = "The local model could not start with the GPU backend."
            )
        }

    internal fun initializeCpuEngine(
        modelFile: File,
        cacheDirectory: File
    ): Result<Unit, IntelligentLifeError> =
        runCatching {
            val newEngine = Engine(
                engineConfig = EngineConfig(
                    modelPath = modelFile.absolutePath,
                    backend = Backend.CPU(),
                    maxNumTokens = 2048,
                    cacheDir = cacheDirectory.absolutePath
                )
            )
            newEngine.initialize()
            engine = newEngine
        }.mapError { throwable ->
            IntelligentLifeError(
                errorMessage = "Local inference initialization failed",
                errorCause = throwable.stackTrace.formatAsMultilineString(),
                errorCompanion = "GPU initialization failed and the CPU fallback could not start."
            )
        }

    @Synchronized
    fun closeLlmEngine() {
        engine?.close()
        engine = null
    }

    /**
     * 查找并返回 LiteRT-LM 要加载的模型文件，不从 APK assets 复制模型。
     *
     * 模型文件必须由用户或外部文件管理器预先放入应用模型目录。默认文件名为 `Qwen.litertlm`，
     * 设备上的路径通常是：
     * `/storage/emulated/0/Android/data/com.baidaidai.anycloud/files/models/Qwen.litertlm`。
     *
     * 查找步骤（与 Rootless Store 的 LocallyLiteRTApi 对齐）：
     * 1. 通过 Default Gateway 获取当前应用的 external files 根目录。
     *    返回 null 表示共享存储卷不可用（未挂载），此时无法创建任何目录，直接返回 [Err]。
     * 2. 在 external files 根目录下定位 `models` 子目录。如果目录不存在，则尝试创建；
     *    创建失败时返回 [Err]。
     * 3. 在 `models` 目录中查找 `Qwen.litertlm`；文件不存在或不是普通文件时返回 [Err]。
     * 4. 文件存在时以 [Ok] 返回，由调用方把其绝对路径传给 [EngineConfig.modelPath]。
     *
     * @return 成功时返回模型文件，失败时返回对应的 [IntelligentLifeError]。
     */
    private fun requireExternalModelFile(): Result<File, IntelligentLifeError> {
        val externalFilesDirectory = androidFileSystemDefaultOperatorGateway
            .findExternalAppFilesDirectoryFile()

        if (externalFilesDirectory == null) {
            return Err(
                error = IntelligentLifeError(
                    errorMessage = "Local model is unavailable",
                    errorCause = "",
                    errorCompanion = "The app-specific external files directory is unavailable."
                )
            )
        }

        val modelDirectory = androidFileSystemCreateOperatorGateway.resolveChildFile(
            parentDirectory = externalFilesDirectory,
            childName = "models"
        )
        val ensuredModelDirectory = androidFileSystemCreateOperatorGateway
            .ensureDirectory(modelDirectory)

        if (ensuredModelDirectory == null) {
            return Err(
                error = IntelligentLifeError(
                    errorMessage = "Local model is unavailable",
                    errorCause = modelDirectory.absolutePath,
                    errorCompanion = "Could not create the app-specific model directory."
                )
            )
        }

        Log.d(
            "AssistantApiLocally",
            "请将 Qwen.litertlm 放置到模型目录：${ensuredModelDirectory.absolutePath}"
        )

        val modelFile = androidFileSystemCreateOperatorGateway.resolveChildFile(
            parentDirectory = ensuredModelDirectory,
            childName = MODEL_FILE_NAME
        )
        val availableModelFile = androidFileSystemSearchOperatorGateway
            .findRegularFile(modelFile)

        if (availableModelFile == null) {
            return Err(
                error = IntelligentLifeError(
                    errorMessage = "Local model is unavailable",
                    errorCause = modelFile.absolutePath,
                    errorCompanion = "Place Qwen.litertlm in the app-specific files/models directory."
                )
            )
        }

        return Ok(value = availableModelFile)
    }

}
