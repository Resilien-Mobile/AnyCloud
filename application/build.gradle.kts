plugins {
    alias(libs.plugins.android.library)
}

android {
    namespace = "com.baidaidai.anycloud.application"
    compileSdk = 37

    defaultConfig {
        minSdk = 36
    }
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":data"))
    implementation(project(":core"))

    implementation("javax.inject:javax.inject:1")

    implementation("androidx.annotation:annotation:1.10.0")
    implementation(libs.kotlinx.coroutines.core)

    // Url detector
    implementation("io.github.url-detector:url-detector:0.1.24")

    // Result
    implementation("com.michael-bull.kotlin-result:kotlin-result:2.3.1")

    testImplementation(libs.junit)
}