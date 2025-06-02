/*
 * Copyright (c) 2024-2025 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSetTree
import org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFramework
import org.pointyware.xyz.buildlogic.buildConfig

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.android.library)
    alias(libs.plugins.compose.multiplatform)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.ksp)
    alias(libs.plugins.xyz.buildConfig)
}

kotlin {
    jvmToolchain(21)
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

    iosX64()
    iosArm64()
    iosSimulatorArm64()

    applyDefaultHierarchyTemplate()
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(projects.coreCommon)
                implementation(projects.coreEntities)
                implementation(projects.coreInteractors)
                implementation(projects.coreData)
                implementation(projects.coreLocal)
                implementation(projects.coreRemote)
                implementation(projects.coreViewModels)
                implementation(projects.coreNavigation)
                implementation(projects.coreUi)

                implementation(libs.coil.core)
                implementation(libs.coil.compose)
                implementation(libs.coil.networkKtor)
                implementation(libs.kotlinx.dateTime)
                implementation(libs.kotlinx.coroutines)
                implementation(libs.koin.core)

                implementation(compose.runtime)
                implementation(compose.material3)
                implementation(libs.compose.navigation)
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
    namespace = "org.pointyware.xyz.drive"
    compileSdk = 35
    defaultConfig {
        minSdk = 24
    }
}

buildConfig {
    packageName = "org.pointyware.xyz.drive"

}
