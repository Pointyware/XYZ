/*
 * Copyright (c) 2025 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.buildlogic

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.io.File

/**
 * Generates a BuildConfig kotlin file for the project that defines all the configured values.
 */
class BuildConfigPlugin: Plugin<Project> {

    override fun apply(target: Project) {
        val buildConfigFile = File(
            target.layout.buildDirectory.asFile.get(),
            "generated/source/buildconfig/${target.name}/BuildConfig.kt"
        )
        val packageName = "${target.group}.${target.name}"
        val group = target.group
        val version = target.version
        target.tasks.register("generateBuildConfig") {
            doLast {
                buildConfigFile.parentFile.mkdirs()
                buildConfigFile.writeText(
                    """
                    package $packageName

                    object BuildConfig {
                        const val GROUP = "$group"
                        const val VERSION_STRING = "$version"
                    }
                    """.trimIndent()
                )
            }
        }

        target.tasks.withType<KotlinCompile>(KotlinCompile::class.java) {
            dependsOn("generateBuildConfig")

            source(buildConfigFile)
        }
        target.extensions.configure(KotlinMultiplatformExtension::class.java) {
            sourceSets.named("commonMain") {
                kotlin.srcDirs(buildConfigFile.parentFile)
            }
        }
    }
}
