/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.KotlinVersion
import org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFramework

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlinSerialization)
}

kotlin {
    jvmToolchain(21)
    @OptIn(ExperimentalKotlinGradlePluginApi::class)
    compilerOptions {
        apiVersion = KotlinVersion.KOTLIN_2_0
    }
    jvm {

    }
    androidTarget {

    }
    val framework = XCFramework()
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64(),
    ).forEach {
        it.binaries.framework {
            baseName = "core_data"
            isStatic = true
            framework.add(this)
        }
    }

    applyDefaultHierarchyTemplate()
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(projects.core.common)
                implementation(projects.core.entities)
                implementation(projects.core.local)
                implementation(projects.core.remote)

                implementation(libs.kotlinx.coroutines)
                implementation(libs.kotlinx.io.core)
                implementation(libs.kotlinx.io.bytestring)
                implementation(libs.koin.core)

                api(libs.kotlinx.serialization)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(libs.kotlin.test)
                implementation(libs.koin.test)
            }
        }

        val jvmSharedMain by creating {
            dependsOn(commonMain)
        }
        val jvmSharedTest by creating {
            dependsOn(commonTest)
        }

        val jvmMain by getting {
            dependsOn(jvmSharedMain)
        }
        val jvmTest by getting {
            dependsOn(jvmSharedTest)
            dependencies {
                implementation(libs.truth)
                implementation(libs.mockk)
            }
        }

        val androidMain by getting {
            dependsOn(jvmSharedMain)
        }
        val androidUnitTest by getting {
            dependsOn(jvmSharedTest)
        }
    }
}

android {
    namespace = "org.pointyware.xyz.core.data"
    compileSdk = 35
    defaultConfig {
        minSdk = 24
    }
}
