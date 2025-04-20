/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.compose.multiplatform)
    alias(libs.plugins.compose.compiler)
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    implementation(projects.core.common)
    implementation(projects.core.data)
    implementation(projects.core.entities)
    implementation(projects.core.interactors)
    implementation(projects.core.local)
    implementation(projects.core.navigation)
    implementation(projects.core.remote)
    implementation(projects.core.ui)
    implementation(projects.core.viewModels)

    implementation(projects.feature.drive)
    implementation(projects.feature.login)
    implementation(projects.feature.ride)

    implementation(projects.appShared)

    implementation(libs.kotlinx.dateTime)
    implementation(libs.koin.core)

    implementation(compose.desktop.currentOs)
    implementation(compose.ui)
    implementation(compose.preview)
    implementation(compose.material3)
    implementation(compose.components.resources)

    implementation(libs.ktor.client.okhttp)

    testImplementation(libs.kotlin.test)
    testImplementation(libs.koin.test)
}

compose.resources {
    publicResClass = true
    packageOfResClass = "org.pointyware.xyz.desktop"
    generateResClass = always
}

compose.desktop {
    application {
        mainClass = "org.pointyware.xyz.desktop.MainKt"
    }
}
