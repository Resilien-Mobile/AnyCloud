plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.jetbrains.kotlin.serialization)
}

android {
    namespace = "com.baidaidai.anycloud.data"
    compileSdk = 37

    defaultConfig {
        minSdk = 36
    }

    buildFeatures {
        aidl = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.ui.tooling.preview)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)

    // Hilt
    testImplementation(libs.hilt.android.testing)
    debugImplementation(libs.androidx.ui.tooling)
    kspTest(libs.hilt.compiler)
    kspAndroidTest(libs.hilt.compiler)
    androidTestImplementation(libs.hilt.android.testing)
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    // Room
    implementation(libs.room.runtime)
    ksp(libs.room.compiler)
    implementation(libs.room.ktx)

    // Shizuku
    implementation("dev.rikka.shizuku:api:13.1.5")
    implementation("dev.rikka.shizuku:provider:13.1.5")

    // Locally
    implementation(project(":domain"))
    implementation(project(":core"))

    // Test
    testImplementation("io.mockk:mockk:1.14.11")

    // Ktor
    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.android)
    implementation(libs.ktor.client.content.negotiation)

    // Kotaml
    implementation(libs.kotaml)

    // Result
    implementation("com.michael-bull.kotlin-result:kotlin-result:2.3.1")

    // ONNX Runtime 由 Sherpa.aar 内置提供（1.28.2），无需 maven 依赖。
    // 待 PP-OCR adapter 回归（ppocr.aar 需要 ai.onnxruntime Java API）时再补：
    // implementation("com.microsoft.onnxruntime:onnxruntime-android:<与 Sherpa 内置同版>")

    // Sherpa（自带 ONNX Runtime .so）
    implementation(
        files("libs/Sherpa.aar")
    )

    // Google MLKit OCR
    implementation("com.google.mlkit:text-recognition-chinese:16.0.1")
    implementation("com.quickbirdstudios:opencv:4.5.3")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.9.0")

    // LiteRT LLM
    implementation(libs.litertlm.android)

    // Json serialization
    api(libs.kotlinx.serialization.json)

    // Json chema - ktor
    // 排除传递引入的旧版 kaml：项目统一使用 kotaml（kaml 的 fork，同名包），
    // 否则 assemble 时 kaml-jvm 0.79 与 kotaml-jvm 0.107 会出现重复类。
    implementation("io.ktor:ktor-openapi-schema:3.6.1") {
        exclude(group = "com.charleskorn.kaml")
    }


}
