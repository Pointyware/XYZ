/*
 * Copyright (c) 2024-2025 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */


plugins {
    `kotlin-dsl`
}

group = "org.pointyware.xyz.buildlogic"

kotlin {
    jvmToolchain(21)
}

dependencies {
    implementation(libs.kotlin.gradle.plugin)
}

gradlePlugin {
    plugins {
        register("koin-dependency-injection-convention") {
            id = "org.pointyware.xyz.koin-dependency-injection-convention"
            implementationClass = "org.pointyware.xyz.buildlogic.KoinDependencyInjectionConventionPlugin"
        }

        register("kmp-convention") {
            id = "org.pointyware.xyz.kmp-convention"
            implementationClass = "org.pointyware.xyz.buildlogic.KmpTargetsConventionPlugin"
        }

        register("build-config") {
            id = "org.pointyware.xyz.build-config"
            implementationClass = "org.pointyware.xyz.buildlogic.BuildConfigPlugin"
        }
    }
}
