/*
 * Copyright (c) 2025 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.buildlogic

import org.gradle.api.Action
import org.gradle.api.DefaultTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.io.File
import java.util.Properties

/**
 * Generates a BuildConfig kotlin file for the project that defines all the configured values.
 */
class BuildConfigPlugin: Plugin<Project> {

    override fun apply(target: Project) {
        val buildConfigPluginExtension = target.extensions.getByType(BuildConfigPluginExtension::class.java)
        val defaultSecretsName = buildConfigPluginExtension.defaultSecretsFileName.get()
        val secretsFileName = buildConfigPluginExtension.secretsFileName.get()
        val defaultSecretsFile = File(target.rootDir, defaultSecretsName)
        val secretsFile = File(target.rootDir, secretsFileName)
        val properties = Properties()
        defaultSecretsFile.inputStream().use {
            properties.load(it)
        }
        secretsFile.inputStream().use {
            properties.load(it)
        }
        val buildConfigFile = File(
            target.layout.buildDirectory.asFile.get(),
            "generated/source/buildconfig/${target.name}/BuildConfig.kt"
        )
        val packageName = "${target.group}.${target.name}"

        val propertiesString = properties.map { (key, value) ->
            "    const val $key = \"$value\""
        }.joinToString("\n")

        target.tasks.register("generateBuildConfig") {
            doLast {
                buildConfigFile.parentFile.mkdirs()
                buildConfigFile.writeText(
                    """
                    package $packageName

                    object BuildConfig {
                    $propertiesString
                    }
                    """.trimIndent()
                )
            }
        }

        target.tasks.withType(KotlinCompile::class.java) {
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

/**
 * Must be called in the build script where the plugin is applied.
 */
fun Project.configureBuildConfigPlugin(
    action: Action<BuildConfigPluginExtension>
) {
    val extension = extensions.create("buildConfig", BuildConfigPluginExtension::class.java)
    action.execute(extension)
}

/**
 * Defines the available properties and methods for the BuildConfig plugin.
 */
abstract class BuildConfigPluginExtension: DefaultTask() {

    @get:Input
    abstract val defaultSecretsFileName: Property<String>
    @get:Input
    abstract val secretsFileName: Property<String>

    @get:Input
    abstract val properties: Property<Map<String, String>>

    init {
        defaultSecretsFileName.convention("secrets.defaults.properties")
        secretsFileName.convention("secrets.properties")
    }

    /**
     * Adds a build config field to the generated BuildConfig file.
     */
    fun buildConfigField(name: String, value: String) {
        properties.set(properties.get() + (name to value))
    }
}
