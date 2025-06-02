/*
 * Copyright (c) 2024-2025 Pointyware. Use of this software is governed by the Affero GPL-3.0 license.
 */

package org.pointyware.xyz.core.common.di

import org.pointyware.xyz.core.common.Environment

/**
 * Controls the injection of objects based on the current environment setting.
 */
interface InjectionController {

    var environment: Environment

    /**
     * Returns an instance of the object based on the current environment setting. If a factory is not found for the current environment, the next environment in the list is used. In order: dev, stage, prod.
     * @throws IllegalStateException if no factory is found for the current environment or any of the subsequent environments.
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
