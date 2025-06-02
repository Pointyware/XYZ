import org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFramework

/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */


plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlinSerialization)
}

kotlin {
    jvmToolchain(21)
    jvm {

    }
    androidTarget {

    }
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    applyDefaultHierarchyTemplate()
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(libs.kotlinx.serialization)
            }
        }
    }
}

android {
    namespace = "org.pointyware.xyz.api.dtos"
    compileSdk = 35
    defaultConfig {
        minSdk = 24
    }
}
