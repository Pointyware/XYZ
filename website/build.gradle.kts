/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

plugins {
    alias(libs.plugins.kotlinJvm)
    application
}

kotlin {
    jvmToolchain(21)
}

dependencies {

    implementation(libs.kotlinx.dateTime)
    implementation(libs.kotlinx.coroutines)
    implementation(libs.koin.core)

    implementation(libs.kotlinx.html)
    implementation(libs.kotlinx.css)

    testImplementation(libs.kotlin.test)
}

application {
    mainClass = "org.pointyware.xyz.site.MainKt"
}
