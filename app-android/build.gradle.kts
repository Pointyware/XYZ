/*
 * Copyright (c) 2024-2025 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

plugins {
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.android.application)
    alias(libs.plugins.compose.multiplatform)
    alias(libs.plugins.compose.compiler)
}

kotlin {
    jvmToolchain(21)
}

android {
    namespace = "org.pointyware.xyz.android"
    compileSdk = 35
    defaultConfig {
        applicationId = "org.pointyware.xyz.android"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        debug {
        }
        release {
            isMinifyEnabled = false
        }
    }
    buildFeatures {
        compose = true
        buildConfig = true
        viewBinding = true
    }
}

dependencies {
    implementation(projects.coreData)
    implementation(projects.coreEntities)
    implementation(projects.coreLocal)
    implementation(projects.coreUi)

    implementation(projects.featureDrive)
    implementation(projects.featureLogin)
    implementation(projects.featureRide)
    implementation(projects.appShared)

    implementation(libs.stripe.android)
    implementation(libs.stripe.connections)

    implementation(libs.androidx.activityCompose)
    implementation(libs.androidx.composeMaterial3)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.constraintlayout)
    debugImplementation(libs.androidx.composeTooling)
    implementation(libs.androidx.composePreview)

    androidTestDebugImplementation(libs.androidx.composeManifest)
    implementation(libs.kotlinx.coroutinesAndroid)
    implementation(libs.koin.core)
    implementation(libs.koin.android)
    implementation(libs.play.services.ads)

    implementation(compose.components.resources)
}

compose.resources {
    publicResClass = true
    packageOfResClass = "org.pointyware.xyz.android"
    generateResClass = always
}
