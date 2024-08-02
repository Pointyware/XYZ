import org.jetbrains.kotlin.gradle.targets.js.dsl.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig

/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */


plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.ktor)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.compose.compiler)
//    `maven-publish`
//    alias(libs.plugins.artifactRegistry)
}

tasks.named<Zip>("distZip") {
    duplicatesStrategy = DuplicatesStrategy.INCLUDE
}
tasks.named<Tar>("distTar") {
    duplicatesStrategy = DuplicatesStrategy.INCLUDE
}

description = "XYZ Wasm App"
version = libs.versions.xyz.get()

kotlin {
    jvmToolchain(17)

    jvm("server") {

    }

    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        val appName = "composeApp"
        moduleName = appName
        browser {
            commonWebpackConfig {
                outputFileName = "$appName.js"
                devServer = (devServer ?: KotlinWebpackConfig.DevServer()).apply {
                    static = (static ?: mutableListOf()).apply {
                        // Serve sources to debug inside browser
                        add(project.projectDir.path)
                        add(project.projectDir.path + "/commonMain/")
                        add(project.projectDir.path + "/wasmJsMain/")
                    }
                }
            }
        }
        binaries.executable()
    }
    sourceSets {
        val commonMain by getting {

            dependencies {
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material)
                implementation(compose.ui)
                implementation(compose.components.resources)
            }
        }
        val commonTest by getting {

        }
        val serverMain by getting {
            dependencies {
                implementation(projects.core.entities)

                implementation(libs.koin.ktor)

                implementation(libs.ktor.server.core)
                implementation(libs.ktor.server.netty)

                implementation(libs.kotlinx.coroutines)
            }
        }
        val serverTest by getting {
            dependencies {
                implementation(libs.kotlin.test)
                implementation(libs.kotlinx.coroutinesTest)
            }
        }
        val wasmJsMain by getting {

        }
        val wasmJsTest by getting {

        }
    }
}

application {
    mainClass = "org.pointyware.xyz.wasm.ServerKt"
}

ktor {
    fatJar {
        archiveFileName = "xyz-wasm-app-${version}.jar"
    }

    docker {
        jreVersion.set(JavaVersion.VERSION_17)
        localImageName = "xyz-wasm-app-image"
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
//            artifactId = "wasm-app"
//            from(components["java"])
//        }
//    }
//    repositories {
//        maven {
//            val releaseURL = "artifactregistry://us-central1-maven.pkg.dev/xyz-debug/maven-repo"
//            url = uri(releaseURL)
//        }
//    }
//}
