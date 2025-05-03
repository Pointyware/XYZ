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
        debug {
        }
        release {
            isMinifyEnabled = false
        }
    }
    buildFeatures {
        compose = true
        buildConfig = true
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

    implementation(libs.google.maps)
    implementation(libs.stripe.android)
    implementation(libs.stripe.connections)

    implementation(libs.androidx.activityCompose)
    implementation(libs.androidx.composeMaterial3)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.fragmentCompose)
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
