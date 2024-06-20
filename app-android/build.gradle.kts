plugins {
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "org.pointyware.xyz.android"
    compileSdk = 34
    defaultConfig {
        applicationId = "org.pointyware.xyz.android"
        minSdk = 21
        targetSdk = 34
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
}

dependencies {
    implementation(project(":core:entities"))
    implementation(project(":core:ui"))

    implementation(project(":app-shared"))

    implementation(libs.androidx.activityCompose)
    implementation(libs.androidx.composeMaterial3)
    debugImplementation(libs.androidx.composeTooling)
    implementation(libs.androidx.composePreview)

    androidTestDebugImplementation(libs.androidx.composeManifest)
    implementation(libs.kotlinx.coroutinesAndroid)
    implementation(libs.koin.core)
    implementation(libs.koin.android)
}
