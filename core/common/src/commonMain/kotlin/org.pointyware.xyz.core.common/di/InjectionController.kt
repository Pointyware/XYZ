/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.core.common.di

import org.pointyware.xyz.core.common.BuildType

/**
 * Controls the injection of objects based on the current environment setting.
 */
interface InjectionController {

    var environment: BuildType

    /**
     * Returns an instance of the object based on the current environment setting. If a factory is not found for the current environment, the next environment in the list is used. In order: dev, stage, prod.
     * @throws IllegalStateException if no factory is found for the current environment or any of the subsequent environments.
     */
    fun <T> inject(factoryMap: Map<BuildType, ()->T>): T
}

internal class InjectionControllerImpl: InjectionController {

    override var environment: BuildType = BuildType.DEBUG

    override fun <T> inject(factoryMap: Map<BuildType, () -> T>): T {
        for (index in environment.ordinal until BuildType.entries.size) {
            val environment = BuildType.entries[index]
            val factory = factoryMap[environment]
            if (factory != null) {
                return factory.invoke()
            }
        }
        throw IllegalStateException("No factory found for environment: $environment")
    }
}

private val singletonInjectionController: InjectionController = InjectionControllerImpl()

fun <T> inject(factoryMap: Map<BuildType, ()->T>): T = singletonInjectionController.inject(factoryMap)
