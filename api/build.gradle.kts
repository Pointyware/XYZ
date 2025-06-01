import org.pointyware.xyz.build.ServerEnvironment
import org.pointyware.xyz.build.release
import org.pointyware.xyz.buildlogic.buildConfig

/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */


plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.ktor)
    alias(libs.plugins.kotlinSerialization)
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
status = ServerEnvironment.Staging

kotlin {
    jvmToolchain(21)
    dependencies {
        implementation(projects.coreDataDtos)
        implementation(projects.coreEntities)

        implementation(libs.koin.ktor)

        implementation(libs.ktor.client.cio)
        implementation(libs.ktor.client.contentNegotiation)
        implementation(libs.ktor.server.auth)
        implementation(libs.ktor.server.core)
        implementation(libs.ktor.server.contentNegotiation)
        implementation(libs.ktor.server.netty)
        implementation(libs.ktor.server.sessions)
        implementation(libs.ktor.server.sse)
        implementation(libs.ktor.serialization.json)

        implementation(libs.postgresql)
        implementation(libs.bouncyCastle)
        implementation(libs.spring.security)

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
            url = uri(artifactRegistryUrl(gCloudRegion, project.properties.getOrDefault("GCLOUD_ID_XYZ_STAGING", "xyz").toString(), gCloudRepo))
            release {
                url = uri(artifactRegistryUrl(gCloudRegion, project.properties.getOrDefault("GCLOUD_ID_XYZ_RELEASE", "xyz").toString(), gCloudRepo))
            }
        }
    }
}

buildConfig {
    packageName = "org.pointyware.xyz.api"
    defaultPropertiesFileName = "local.defaults.properties"
    overridingPropertiesFileName = "secrets.properties"

    fromProperties(project.file("secrets.properties")) {
        addStringNamed("STRIPE_API_KEY", "STRIPE_API_KEY_TEST")
        release {
            addStringNamed("STRIPE_API_KEY", "STRIPE_API_KEY_LIVE")
        }
    }
}
