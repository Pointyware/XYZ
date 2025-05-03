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
    ":core-data-dtos"
)
include(":core:common")
include(":core:data")
include(":core:entities")
include(":core:interactors")
include(":core:local")
include(":core:navigation")
include(":core:remote")
include(":core:ui")
include(":core:view-models")

include(":feature:manage")
include(":feature:drive")
include(":feature:login")
include(":feature:ride")

include(":api")
include(":app-shared")
include(":app-android")
//include(":app-ios")
include(":app-desktop")
