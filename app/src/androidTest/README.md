# Intelligent Life 模型设备测试

`IntelligentLifeGatewayInstrumentedTest` 运行在 `app` 模块，直接使用正式应用的模型文件，不需要复制到单独的测试应用目录。模型位置：

```text
/sdcard/Android/data/com.baidaidai.anycloud/files/models/Qwen.litertlm
```

在 Android Studio 中打开 `IntelligentLifeGatewayInstrumentedTest.kt`，点击测试旁边的运行按钮即可。测试使用正式应用的单例依赖，直接调用 `IntelligentLifeGatewayImpl.resolveMaskedDto`，不运行 OCR。它检查品牌为“咖啡”或 `null`、取餐码为“168”、`content` 包含“冰吸生椰拿铁”；模型路径、大小和完整 DTO 会输出到 Logcat 的 `IntelligentLifeGatewayTest` 标签。

测试默认使用 CPU，避免设备没有 OpenCL 时无法推理。如需使用应用的默认后端选择，在 Android Studio 的测试运行配置中添加 Instrumentation argument `backend=auto`。

项目的 `gradle.properties` 设置了 `android.injected.androidTest.leaveApksInstalledAfterRun=true`，防止 Gradle 测试结束后卸载应用并删除模型目录。模型仍需要预先放在正式应用目录中；若替换模型，请确保文件名为 `Qwen.litertlm`。
