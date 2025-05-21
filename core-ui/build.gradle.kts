/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.KotlinVersion
import org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFramework
import java.util.Properties

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.android.library)
    alias(libs.plugins.compose.multiplatform)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.gms.maps)
}

kotlin {
    jvmToolchain(21)
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
            baseName = "core_ui"
            isStatic = true
            framework.add(this)
        }
    }

    applyDefaultHierarchyTemplate()
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(projects.coreCommon)
                implementation(projects.coreEntities)
                implementation(projects.coreViewModels)

                implementation(compose.ui)
                implementation(compose.material3)
                api(compose.materialIconsExtended)
                implementation(compose.components.resources)
                implementation(compose.components.uiToolingPreview) // fleet support
                implementation(libs.compose.lifecycle)

                implementation(libs.coil.core)
                implementation(libs.coil.compose)
                implementation(libs.coil.networkKtor)
                implementation(libs.kotlinx.dateTime)
                implementation(libs.kotlinx.coroutines)
                implementation(libs.koin.core)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(libs.kotlin.test)
                implementation(libs.koin.test)
                implementation(libs.kotlinx.coroutinesTest)

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
            }
        }

        val androidMain by getting {
            dependsOn(jvmSharedMain)
            dependencies {
                implementation(libs.androidx.fragmentCompose)

                implementation(libs.androidx.composePreview)
                implementation(libs.play.services.ads)

                implementation(libs.google.maps)
                implementation(libs.stripe.android)
                implementation(libs.stripe.connections)
            }
        }
        val androidUnitTest by getting {
            dependsOn(jvmSharedTest)
        }
    }
}

compose.resources {
    publicResClass = true
    packageOfResClass = "org.pointyware.xyz.ui"
    generateResClass = always
}

android {
    namespace = "org.pointyware.xyz.ui"
    compileSdk = 35
    defaultConfig {
        minSdk = 24
    }
    @Suppress("UnstableApiUsage")
    testOptions {
        targetSdk = 35
    }
    buildFeatures {
        buildConfig = true
        compose = true
    }
    buildTypes {
        val defaults = rootProject.file("stripe-local.defaults.properties")
        val secrets = rootProject.file("stripe-secrets.properties")
        val properties = Properties()
        defaults.inputStream().use {
            properties.load(it)
        }
        secrets.inputStream().use {
            properties.load(it)
        }
        debug {
            buildConfigField("String", "STRIPE_API_KEY", "\"${properties["STRIPE_API_KEY_TEST"]}\"")
            buildConfigField("String", "STRIPE_BACKEND_URL", "\"${properties["STRIPE_STAGING_URL"]}\"")
        }
        release {
            buildConfigField("String", "STRIPE_API_KEY", "\"${properties["STRIPE_API_KEY_LIVE"]}\"")
            buildConfigField("String", "STRIPE_BACKEND_URL", "\"${properties["STRIPE_PRODUCTION_URL"]}\"")
        }
    }
}

dependencies {
    debugImplementation(libs.androidx.composePreview)
    implementation(libs.androidx.composeTooling)
}

/*
 All properties will be read from both files, with propertiesFileName overwriting
   properties from defaultPropertiesFileName if they are present in both, and then
   added to the BuildConfig class.
 This method therefore does not support debug/release specific properties. Including a release
   key in the debug build could be a security issue, so if some API does not have a test mode
   you should use a different key for the debug build, which will require a different
   approach to prevent debug/release keys from being included in the same build.
 */
secrets {
    // Exclude from VC
    propertiesFileName = "secrets.properties"
    // This can be included and can be helpful for setting up a local dev environment
    defaultPropertiesFileName = "local.defaults.properties"
}
