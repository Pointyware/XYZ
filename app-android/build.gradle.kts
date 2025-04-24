/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

plugins {
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.android.application)
    alias(libs.plugins.compose.multiplatform)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.gms.maps)
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
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        buildConfig = true
        viewBinding = true
    }
}

dependencies {
    implementation(projects.core.entities)
    implementation(projects.core.local)
    implementation(projects.core.ui)

    implementation(projects.feature.drive)
    implementation(projects.feature.login)
    implementation(projects.feature.ride)
    implementation(projects.appShared)

    implementation(libs.androidx.activityCompose)
    implementation(libs.androidx.composeMaterial3)
    implementation(libs.google.maps)
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

secrets {
    // Exclude from VC
    propertiesFileName = "secrets.properties"
    // This can be included and can be helpful for setting up a local dev environment
    defaultPropertiesFileName = "local.defaults.properties"
}
