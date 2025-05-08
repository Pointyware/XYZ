/*
 * Copyright (c) 2025 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.buildlogic

import org.gradle.api.Action
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.provider.MapProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinPlatformJvmPlugin
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinMultiplatformPlugin
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.io.File
import java.util.Properties

/**
 * Generates a BuildConfig kotlin file for the project that defines all the configured values.
 */
class BuildConfigPlugin: Plugin<Project> {

    override fun apply(target: Project) {
        val buildConfigFile = File(
            target.layout.buildDirectory.asFile.get(),
            "generated/source/buildconfig/${target.name}/BuildConfig.kt"
        )
        val extension = target.extensions.create("buildConfig", BuildConfigPluginExtension::class.java)
        extension.packageName.convention("${target.group}")
        extension.defaultSecretsFileName.convention("secrets.defaults.properties")
        extension.secretsFileName.convention("secrets.properties")
        extension.properties.convention(mapOf())

        target.tasks.register("generateBuildConfig") {
            group = "build"
            description = "Generates a BuildConfig file with the configured properties."

            val defaultSecretsName = extension.defaultSecretsFileName.get()
            val secretsFileName = extension.secretsFileName.get()
            val defaultSecretsFile = target.file(defaultSecretsName)
            val secretsFile = target.file(secretsFileName)
            val properties = Properties()
            defaultSecretsFile.inputStream().use {
                properties.load(it)
            }
            secretsFile.inputStream().use {
                properties.load(it)
            }
            extension.properties.get().forEach {
                properties.setProperty(it.key, it.value)
            }

            val packageName = extension.packageName.get()

            val propertiesString = properties.map { (key, value) ->
                "    const val $key = \"$value\""
            }.joinToString("\n")
            doLast {
                buildConfigFile.parentFile.mkdirs()
                buildConfigFile.writeText(
"""package $packageName

object BuildConfig {
$propertiesString
}
"""
                )
            }
        }

        target.tasks.withType(KotlinCompile::class.java) {
            dependsOn("generateBuildConfig")

            source(buildConfigFile)
        }
        when {
            // Support Kotlin Multiplatform projects
            target.plugins.hasPlugin(KotlinMultiplatformPlugin::class.java) -> {
                target.extensions.configure(KotlinMultiplatformExtension::class.java) {
                    sourceSets.named("commonMain") {
                        kotlin.srcDirs(buildConfigFile.parentFile)
                    }
                }
            }
            // Support Kotlin JVM projects
            target.plugins.hasPlugin(KotlinPlatformJvmPlugin::class.java) -> {
                target.extensions.configure(KotlinJvmProjectExtension::class.java) {
                    sourceSets.named("main") {
                        kotlin.srcDirs(buildConfigFile.parentFile)
                    }
                }
            }
        }
    }
}

/**
 * Must be called in the build script where the plugin is applied.
 */
fun Project.buildConfig(
    action: Action<BuildConfigPluginExtension>
) {
    extensions.configure(BuildConfigPluginExtension::class.java, action)
}

/**
 * Defines the available properties and methods for the BuildConfig plugin.
 */
abstract class BuildConfigPluginExtension {

    @get:Input
    abstract val packageName: Property<String>
    @get:Input
    abstract val defaultSecretsFileName: Property<String>
    @get:Input
    abstract val secretsFileName: Property<String>

    @get:Input
    abstract val properties: MapProperty<String, String>

    fun addString(name: String, value: String) {
        properties.set(properties.get() + (name to value))
    }

    inner class LoadPropertiesScope(
        private val properties: Properties
    ) {
        fun addString(key: String) {
            val value = properties.getProperty(key)
            if (value != null) {
                addString(key, value)
            } else {
                throw IllegalArgumentException("Key $key not found in properties file.")
            }
        }

        /**
         * Retrieves the property associated with [alias] and retains it under the given [key] for
         * writing to the output BuildConfig.
         */
        fun addStringAlias(key: String, alias: String) {
            val value = properties.getProperty(alias)
            if (value != null) {
                addString(key, value)
            } else {
                throw IllegalArgumentException("Key $alias not found in properties file.")
            }
        }
    }

    fun loadProperties(fileName: String, block: LoadPropertiesScope.() -> Unit) {
        val file = File(fileName)
        if (!file.exists()) {
            throw IllegalArgumentException("File $fileName does not exist.")
        }
        val properties = Properties()
        file.inputStream().use {
            properties.load(it)
        }
        val scope = LoadPropertiesScope(properties)
        scope.block()
    }
}
