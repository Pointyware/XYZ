/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.core.common.di

enum class Environment {
    DEV, STAGE, PROD
}

/**
 * Controls the injection of objects based on the current environment setting.
 */
interface InjectionController {

    var environment: Environment

    /**
     * Returns an instance of the object based on the current environment setting. If a factory is not found for the current environment, the next environment in the list is used. In order: dev, stage, prod.
     */
    fun <T> inject(factoryMap: Map<Environment, ()->T>): T
}

internal class InjectionControllerImpl: InjectionController {

    override var environment: Environment = Environment.DEV

    override fun <T> inject(factoryMap: Map<Environment, () -> T>): T {
        for (index in environment.ordinal until Environment.entries.size) {
            val environment = Environment.entries[index]
            val factory = factoryMap[environment]
            if (factory != null) {
                return factory.invoke()
            }
        }
        throw IllegalStateException("No factory found for environment: $environment")
    }
}

private val singletonInjectionController: InjectionController = InjectionControllerImpl()

fun <T> inject(factoryMap: Map<Environment, ()->T>): T = singletonInjectionController.inject(factoryMap)
