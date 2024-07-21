/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.core.common.di

const val devEnvironment = "dev"
const val stageEnvironment = "stage"
const val prodEnvironment = "prod"

/**
 * Controls the injection of objects based on the current environment setting.
 */
interface InjectionController {

    var environment: String

    /**
     * Returns an instance of the object based on the current environment setting. If a factory is not found for the current environment, the next environment in the list is used. In order: dev, stage, prod.
     */
    fun <T> inject(factoryMap: Map<String, ()->T>): T
}

object SingletonInjectionController: InjectionController {

    private val environmentList = listOf(devEnvironment, stageEnvironment, prodEnvironment)

    override var environment: String = devEnvironment

    override fun <T> inject(factoryMap: Map<String, () -> T>): T {
        require(factoryMap.keys.none { it !in listOf(devEnvironment, stageEnvironment, prodEnvironment) }) {
            "Factory map keys must be one of the following: $devEnvironment, $stageEnvironment, $prodEnvironment"
        }
        //
        val factoryIndex = environmentList.indexOf(environment).takeIf { it >= 0 } ?: devEnvironment

        val factory = factoryMap[environment] ?: throw IllegalStateException("No factory found for environment: $environment")
        return factory.invoke()
    }
}

fun <T> inject(factoryMap: Map<String, ()->T>): T = SingletonInjectionController.inject(factoryMap)
