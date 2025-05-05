/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

import org.jetbrains.dokka.gradle.DokkaTask
import org.pointyware.xyz.build.ClientBuild
import java.net.URL

plugins {
    //trick: for the same plugin versions in all sub-modules
    alias(libs.plugins.android.application).apply(false)
    alias(libs.plugins.android.library).apply(false)
    alias(libs.plugins.compose.multiplatform).apply(false)
    alias(libs.plugins.compose.compiler).apply(false)
    alias(libs.plugins.gms.maps).apply(false)
    alias(libs.plugins.kotlinAndroid).apply(false)
    alias(libs.plugins.kotlinMultiplatform).apply(false)
    alias(libs.plugins.kotlinJvm).apply(false)

    // apply dokka now
    alias(libs.plugins.dokka)

    alias(libs.plugins.xyz.koin).apply(false)
    alias(libs.plugins.xyz.kmp).apply(false)
    alias(libs.plugins.xyz.buildConfig).apply(false)
    alias(libs.plugins.artifactRegistry).apply(false)
}

group = "org.pointyware.xyz"
version = libs.versions.xyz.get()
description = "Get your X from Y to Z!"
status = ClientBuild.Debug

tasks.dokkaHtmlMultiModule {
    moduleName.set("XYZ")
}

subprojects {
    apply(plugin = "org.jetbrains.dokka")

    tasks.withType<DokkaTask>().configureEach {

        dokkaSourceSets.configureEach {

            sourceLink {
                localDirectory.set(projectDir.resolve("src"))
                remoteUrl.set(URL("https://github.com/Pointyware/XYZ/tree/main/src"))
                remoteLineSuffix.set("#L")
            }
        }
    }
}
