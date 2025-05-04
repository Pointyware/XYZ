/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
        mavenLocal()
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        mavenLocal()
    }
}

rootProject.name = "XYZ"
include(
    ":core-common",
    ":core-data",
    ":core-data-dtos",
    ":core-entities",
    ":core-interactors",
    ":core-local",
    ":core-navigation",
    ":core-remote",
    ":core-ui",
    ":core-view-models"
)
include(
    ":feature-manage",
    ":feature-drive",
    ":feature-login",
    ":feature-ride",
)

include(":api")
include(":app-shared")
include(":app-android")
//include(":app-ios")
include(":app-desktop")
