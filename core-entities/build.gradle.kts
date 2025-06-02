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
                implementation(projects.coreCommon)

                implementation(libs.kotlinx.dateTime)
                implementation(libs.kotlinx.coroutines)

                implementation(libs.koin.core)
                implementation(libs.kotlinx.serialization)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(libs.kotlin.test)
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
    namespace = "org.pointyware.xyz.entities"
    compileSdk = 35
    defaultConfig {
        minSdk = 24
    }
}
