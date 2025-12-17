import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

import com.codingfeline.buildkonfig.compiler.FieldSpec.Type.BOOLEAN

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.buildkonfig)
}

kotlin {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }

    sourceSets {
        commonMain.dependencies {
            // Compose Multiplatform
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)

            // Navigation (Multiplatform)
            implementation(libs.navigation.compose)

            // ViewModel & Lifecycle (Multiplatform)
            implementation(libs.lifecycle.viewmodel)
            implementation(libs.lifecycle.viewmodel.compose)
            implementation(libs.lifecycle.runtime.compose)

            // QR Code Scanner
            implementation(libs.qrkit)

            // DI with ViewModel support
            implementation(libs.koin.core)
            implementation(libs.koin.compose)
            implementation(libs.koin.compose.viewmodel)

            // Ktor
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.serialization.kotlinx.json)
            implementation(libs.ktor.client.logging)

            // Kotlinx
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.kotlinx.serialization.json)

            // Coil - Image Loading
            implementation(libs.coil.compose)
        }

        androidMain.dependencies {
            implementation(libs.ktor.client.android)
            implementation(libs.koin.android)
            implementation(libs.androidx.activity.compose)
            implementation(libs.androidx.lifecycle.runtime)
            implementation(compose.uiTooling)
            implementation(libs.coil.network.okhttp)
        }

        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
            implementation(libs.coil.network.ktor)
        }
    }
}

android {
    namespace = "cut.the.crap.jukestar"
    compileSdk = 34

    defaultConfig {
        applicationId = "cut.the.crap.jukestar"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    buildFeatures {
        compose = true
    }
}

buildkonfig {
    packageName = "com.hitit.app"

    // Flavor to use for commonMain generation
    defaultConfigs {
        buildConfigField(BOOLEAN, "IS_DEBUG", "false")
        buildConfigField(BOOLEAN, "IS_RELEASE_PREVIEW", "false")
    }

    targetConfigs {
        create("android") {
            buildConfigField(BOOLEAN, "IS_DEBUG", "true")
            buildConfigField(BOOLEAN, "IS_RELEASE_PREVIEW", "true")
        }
        create("iosArm64") {
            buildConfigField(BOOLEAN, "IS_DEBUG", "true")
            buildConfigField(BOOLEAN, "IS_RELEASE_PREVIEW", "true")
        }
        create("iosX64") {
            buildConfigField(BOOLEAN, "IS_DEBUG", "true")
            buildConfigField(BOOLEAN, "IS_RELEASE_PREVIEW", "true")
        }
        create("iosSimulatorArm64") {
            buildConfigField(BOOLEAN, "IS_DEBUG", "true")
            buildConfigField(BOOLEAN, "IS_RELEASE_PREVIEW", "true")
        }
    }
}
