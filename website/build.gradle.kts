/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

import org.jetbrains.compose.ExperimentalComposeLibrary
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.KotlinVersion
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSetTree
import org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFramework

plugins {
    alias(libs.plugins.kotlinJvm)
}

kotlin {
    jvmToolchain(21)
}

dependencies {

    implementation(libs.kotlinx.dateTime)
    implementation(libs.kotlinx.coroutines)
    implementation(libs.koin.core)

    testImplementation(libs.kotlin.test)
}
