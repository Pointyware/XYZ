import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.KotlinVersion
import org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFramework

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.xyz.koin)
    alias(libs.plugins.xyz.kmp)
}

kotlin {
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
            baseName = "app_shared"
            isStatic = true
            framework.add(this)
        }
    }

    applyDefaultHierarchyTemplate()
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(project(":core:common"))
                implementation(project(":core:data"))
                implementation(project(":core:entities"))
                implementation(project(":core:interactors"))
                implementation(project(":core:local"))
                implementation(project(":core:navigation"))
                implementation(project(":core:remote"))
                implementation(project(":core:ui"))
                implementation(project(":core:view-models"))

                implementation(project(":feature:drive"))
                implementation(project(":feature:login"))
                implementation(project(":feature:ride"))

                implementation(compose.ui)
                implementation(compose.material3)
                implementation(compose.components.resources)
                implementation(compose.components.uiToolingPreview) // fleet support

                implementation(libs.kotlinx.dateTime)
                implementation(libs.kotlinx.coroutines)
                implementation(libs.koin.core)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(libs.kotlin.test)
            }
        }

        val jvmMain by getting {
            dependencies {
                implementation(compose.desktop.common)
                implementation(libs.kotlinx.coroutinesSwing)
            }
        }
    }
}
compose.resources {
    publicResClass = true
    packageOfResClass = "org.pointyware.xyz.shared"
    generateResClass = always
}

android {
    namespace = "org.pointyware.xyz.shared"
    compileSdk = 34
    defaultConfig {
        minSdk = 21
    }
}
