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
    jvmToolchain(17)
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
            baseName = "core_local"
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

                implementation(libs.kotlinx.coroutines)
                implementation(libs.kotlinx.serialization)
                api(libs.kotlinx.io.core)
                api(libs.kotlinx.io.bytestring)

                implementation(libs.koin.core)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(libs.kotlin.test)

                implementation(libs.kotlinx.coroutinesTest)
            }
        }

        val jvmSharedMain by creating {
            dependsOn(commonMain)

            dependencies {
                implementation(libs.kotlinx.coroutinesCoreJvm)
            }
        }
        val jvmSharedTest by creating {
            dependsOn(commonTest)
        }

        val jvmMain by getting {
            dependsOn(jvmSharedMain)

            dependencies {
                implementation(libs.kotlinx.coroutinesSwing)
            }
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

            dependencies {
                implementation(libs.kotlinx.coroutinesAndroid)
            }
        }
        val androidUnitTest by getting {
            dependsOn(jvmSharedTest)
        }
    }
}

android {
    namespace = "org.pointyware.xyz.core.local"
    compileSdk = 34
    defaultConfig {
        minSdk = 24
    }
}
