/*
 * Copyright (c) 2024 Pointyware
 */

import org.jetbrains.dokka.gradle.DokkaMultiModuleTask
import org.jetbrains.dokka.gradle.DokkaTask
import java.net.URL

plugins {
    //trick: for the same plugin versions in all sub-modules
    alias(libs.plugins.androidApplication).apply(false)
    alias(libs.plugins.androidLibrary).apply(false)
    alias(libs.plugins.composeMultiplatform).apply(false)
    alias(libs.plugins.compose.compiler).apply(false)
    alias(libs.plugins.kotlinAndroid).apply(false)
    alias(libs.plugins.kotlinMultiplatform).apply(false)
    alias(libs.plugins.kotlinJvm).apply(false)

    // apply dokka now
    alias(libs.plugins.dokka)

    alias(libs.plugins.xyz.koin).apply(false)
    alias(libs.plugins.xyz.kmp).apply(false)
    alias(libs.plugins.artifactRegistry).apply(false)
}

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
