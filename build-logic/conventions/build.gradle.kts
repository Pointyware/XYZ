/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */


plugins {
    `kotlin-dsl`
}

group = "org.pointyware.xyz.buildlogic"

kotlin {
    jvmToolchain(17)
}

dependencies {
    implementation(libs.kotlin.gradle.plugin)
}

gradlePlugin {
    plugins {
        create("koin-dependency-injection-convention") {
            id = "org.pointyware.xyz.koin-dependency-injection-convention"
            implementationClass = "org.pointyware.xyz.buildlogic.KoinDependencyInjectionConventionPlugin"
        }

        create("kmp-convention") {
            id = "org.pointyware.xyz.kmp-convention"
            implementationClass = "org.pointyware.xyz.buildlogic.KmpTargetsConventionPlugin"
        }
    }
}
