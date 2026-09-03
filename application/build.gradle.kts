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

    implementation("javax.inject:javax.inject:1")

    implementation("androidx.annotation:annotation:1.10.0")
    implementation(libs.kotlinx.coroutines.core)

    testImplementation(libs.junit)
}