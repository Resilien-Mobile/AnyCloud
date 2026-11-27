
plugins {
    kotlin("jvm")
    alias(libs.plugins.jetbrains.kotlin.serialization)
}

dependencies {
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.serialization.core)
    implementation("com.michael-bull.kotlin-result:kotlin-result:2.3.1")
}
