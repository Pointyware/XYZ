/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.KotlinVersion
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSetTree
import org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFramework

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.ksp)
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
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        instrumentedTestVariant {
            sourceSetTree.set(KotlinSourceSetTree.test)

            dependencies {
                implementation(libs.androidx.composeTest)
                debugImplementation(libs.androidx.composeManifest)
            }
        }
    }
    val framework = XCFramework()
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64(),
    ).forEach {
        it.binaries.framework {
            baseName = "feature_drive"
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
                implementation(projects.core.interactors)
                implementation(projects.core.data)
                implementation(projects.core.local)
                implementation(projects.core.remote)
                implementation(projects.core.viewModels)
                implementation(projects.core.navigation)
                implementation(projects.core.ui)

                implementation(libs.coil.core)
                implementation(libs.coil.compose)
                implementation(libs.coil.networkKtor)
                implementation(libs.kotlinx.dateTime)
                implementation(libs.kotlinx.coroutines)
                implementation(libs.koin.core)

                implementation(compose.runtime)
                implementation(compose.material3)
                implementation(compose.components.uiToolingPreview) // fleet support
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(libs.kotlin.test)
                implementation(libs.koin.test)
                implementation(libs.kotlinx.coroutinesTest)
                implementation(libs.turbine)

                @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
                implementation(compose.uiTest)
                implementation(libs.mockative)
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
            dependencies {
                implementation(compose.preview) // android/desktop support
                implementation(compose.desktop.currentOs)
            }
        }
        val jvmTest by getting {
            dependsOn(jvmSharedTest)
            dependencies {
                implementation(libs.truth)
                implementation(libs.mockk)
                implementation(libs.jupiter)
            }
        }

        val androidMain by getting {
            dependsOn(jvmSharedMain)
            dependencies {
                implementation(libs.koin.core)
                implementation(libs.koin.android)
                implementation(libs.androidx.composePreview)
            }
        }
        val androidUnitTest by getting {
            dependsOn(jvmSharedTest)
            dependencies {
                implementation(libs.koin.test)
            }
        }

        val iosMain by getting {
            dependencies {
            }
        }

        val iosTest by getting {
            dependencies {

            }
        }
    }
}

dependencies {
    configurations
        .filter { it.name.startsWith("ksp") && it.name.contains("Test") }
        .forEach {
            add(it.name, libs.mockativeSymbolProcessor)
        }
}

android {
    namespace = "org.pointyware.xyz.feature.drive"
    compileSdk = 34
    defaultConfig {
        minSdk = 21
    }
}
