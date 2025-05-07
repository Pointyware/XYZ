import org.pointyware.xyz.build.release
import org.pointyware.xyz.buildlogic.buildConfig

/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */


plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.ktor)
    `maven-publish`
//    alias(libs.plugins.artifactRegistry)
    alias(libs.plugins.xyz.buildConfig)
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
    jvmToolchain(21)
    dependencies {
        implementation(projects.coreDataDtos)
        implementation(projects.coreEntities)

        implementation(libs.koin.ktor)

        implementation(libs.ktor.server.core)
        implementation(libs.ktor.server.netty)

        implementation(libs.stripe.server)

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
}

private fun artifactRegistryUrl(region: String, project: String, repo: String): String {
    return "artifactregistry://$region-maven.pkg.dev/$project/$repo"
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = group.toString()
            artifactId = "api"
            from(components["java"])
        }
    }
    repositories {
        maven {
            val gCloudRegion = "us-south1"
            val gCloudRepo = "docker-repo"
            url = uri(artifactRegistryUrl(gCloudRegion, "xyz-debug", gCloudRepo))
            release {
                url = uri(artifactRegistryUrl(gCloudRegion, "xyz-release", gCloudRepo))
            }
        }
    }
}

buildConfig {
    packageName = "org.pointyware.xyz.api"
}
