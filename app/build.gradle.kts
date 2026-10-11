plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.ksp)

    // Navigation3 Kotlin Plugin
    alias(libs.plugins.jetbrains.kotlin.serialization)
}

android {
    namespace = "com.baidaidai.anycloud"
    compileSdk = 37

    defaultConfig {
        applicationId = "com.baidaidai.anycloud"
        minSdk = 36
        targetSdk = 36
        versionCode = 2
        versionName = "0.1.1"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    signingConfigs {
        create("github") {
            storeFile = file("../release.jks")
            storePassword = System.getenv("DEBUG_STORE_PASSWORD")
            keyAlias = System.getenv("DEBUG_KEY_ALIAS")
            keyPassword = System.getenv("DEBUG_KEY_PASSWORD")
        }
    }

    buildTypes {
        release {
            optimization {
                enable = false
            }
        }
        create("github") {
            initWith(getByName("release"))
            signingConfig = signingConfigs.getByName("github")
            matchingFallbacks += listOf("release")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    buildFeatures {
        compose = true
    }

}

dependencies {
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
    debugImplementation(libs.androidx.compose.ui.tooling)

    // Hilt
    testImplementation(libs.hilt.android.testing)
    debugImplementation(libs.androidx.ui.tooling)
    kspTest(libs.hilt.compiler)
    kspAndroidTest(libs.hilt.compiler)
    androidTestImplementation(libs.hilt.android.testing)
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    // Shizuku
    implementation("dev.rikka.shizuku:api:13.1.5")
    implementation("dev.rikka.shizuku:provider:13.1.5")


    // Locally
    implementation(project(":ui"))
    implementation(project(":application"))

}
