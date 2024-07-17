/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */


plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.ktor)
    `maven-publish`
    alias(libs.plugins.artifactRegistry)
}

tasks.named<Zip>("distZip") {
    duplicatesStrategy = DuplicatesStrategy.INCLUDE
}
tasks.named<Tar>("distTar") {
    duplicatesStrategy = DuplicatesStrategy.INCLUDE
}

description = "XYZ API"
version = libs.versions.xyz.get()

kotlin {
    dependencies {
        implementation(projects.core.entities)

        implementation(libs.koin.ktor)

        implementation(libs.ktor.server.core)
        implementation(libs.ktor.server.netty)

        implementation(libs.kotlinx.coroutines)

        testImplementation(libs.kotlin.test)
        testImplementation(libs.kotlinx.coroutinesTest)
    }
}

application {
    mainClass = "org.pointyware.replace-me.api.ServerKt"
}

ktor {
    fatJar {
        archiveFileName = "replace-me-API-${version}.jar"
    }
}

//publishing {
//    publications {
//        create<MavenPublication>("maven") {
//            groupId = "org.pointyware.replace-me"
//            artifactId = "replace-me-api"
//            from(components["java"])
//        }
//    }
//    repositories {
//        maven {
//            val releaseURL = "artifactregistry://us-central1-maven.pkg.dev/<project-id>/<repo>"
//            url = uri(releaseURL)
//        }
//    }
//}
