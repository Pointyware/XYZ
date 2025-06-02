/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

import org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFramework
import org.pointyware.xyz.build.ServerEnvironment
import org.pointyware.xyz.build.local
import org.pointyware.xyz.build.release
import org.pointyware.xyz.build.staging

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.android.library)
    alias(libs.plugins.xyz.buildConfig)
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
                implementation(projects.apiDtos)
                implementation(projects.coreCommon)
                implementation(projects.coreEntities)

                implementation(libs.koin.core)

                api(libs.ktor.client.core)
                api(libs.ktor.client.contentNegotiation)
                api(libs.ktor.client.resources)
                api(libs.ktor.client.serialization)
                api(libs.ktor.serialization.json)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(libs.kotlin.test)
                implementation(libs.ktor.client.mock)
            }
        }

        val jvmSharedMain by creating {
            dependsOn(commonMain)

            dependencies {
                implementation(libs.ktor.client.okhttp)
            }
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

        val nativeMain by getting {
            dependencies {
                implementation(libs.ktor.client.cio)
            }
        }
        val nativeTest by getting {

        }
    }
}

android {
    namespace = "org.pointyware.xyz.remote"
    compileSdk = 35
    defaultConfig {
        minSdk = 24
    }
}

status = ServerEnvironment.Local
buildConfig {
    packageName = "org.pointyware.xyz.remote"
    defaultPropertiesFileName = "local.defaults.properties"
    overridingPropertiesFileName = "secrets.properties"
    fromProperties(project.file("secrets.properties")) {
        local {
            addStringNamed("API_HOST_URI", "API_HOST_LOCAL")
            addString("API_HOST_SECURE", "false")
        }
        staging {
            addStringNamed("API_HOST_URI", "API_HOST_STAGING")
            addString("API_HOST_SECURE", "true")
        }
        release {
            addStringNamed("API_HOST_URI", "API_HOST_RELEASE")
            addString("API_HOST_SECURE", "true")
        }
    }
}
