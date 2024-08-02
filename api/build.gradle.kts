/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */


plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.ktor)
//    `maven-publish`
//    alias(libs.plugins.artifactRegistry)
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
    jvmToolchain(17)
    dependencies {
        implementation(projects.core.entities)

        implementation(libs.koin.ktor)

        implementation(libs.ktor.server.core)
        implementation(libs.ktor.server.netty)

        implementation(libs.kotlinx.coroutines)

        testImplementation(libs.kotlin.test)
        testImplementation(libs.kotlinx.coroutinesTest)
        testImplementation(libs.ktor.server.test.host)
    }
}

application {
    mainClass = "org.pointyware.xyz.api.ServerKt"
}

ktor {
    fatJar {
        archiveFileName = "XYZ-API-${version}.jar"
    }

    docker {
        jreVersion.set(JavaVersion.VERSION_17)
        localImageName = "xyz-api-image"
//        portMappings.set(listOf(
//            io.ktor.plugin.features.DockerPortMapping(
//                80,
//                8080,
//                io.ktor.plugin.features.DockerPortMappingProtocol.TCP
//            )
//        ))
//        externalRegistry = DockerImageRegistry.externalRegistry(
//            username = providers.environmentVariable(""),
//            password = providers.environmentVariable(""),
//            hostname = providers.provider { "" },
//            project = providers.provider { "" },
//            namespace = providers.provider { "" }
//        )
    }
}

//publishing {
//    publications {
//        create<MavenPublication>("maven") {
//            groupId = "org.pointyware.xyz"
//            artifactId = "xyz-api"
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
