/*
 * Copyright (c) 2025 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.buildlogic

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.io.File

/**
 * Generates a BuildConfig kotlin file for the project that defines all the configured values.
 */
class BuildConfigPlugin: Plugin<Project> {

    override fun apply(target: Project) {
        target.tasks.register("generateBuildConfig") {
            doLast {
                val buildConfigFile = File(
                    target.layout.buildDirectory.asFile.get(),
                    "generated/source/buildconfig/${target.name}/BuildConfig.kt"
                )
                buildConfigFile.parentFile.mkdirs()
                buildConfigFile.writeText(
                    """
                    package ${target.group}.${target.name}

                    object BuildConfig {
                        const val GROUP = "${target.group}"
                        const val VERSION_STRING = "${target.version}"
                    }
                    """.trimIndent()
                )
            }
        }

        target.tasks.withType<KotlinCompile>(KotlinCompile::class.java) {
            dependsOn("generateBuildConfig")
        }
    }
}
