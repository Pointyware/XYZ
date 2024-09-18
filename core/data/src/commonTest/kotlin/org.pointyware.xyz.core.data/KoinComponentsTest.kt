/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.core.data

import io.ktor.http.ContentType
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.module.dsl.scopedOf
import org.koin.dsl.module
import org.koin.mp.KoinPlatform.getKoin
import org.pointyware.xyz.core.data.di.ApplicationComponent
import org.pointyware.xyz.core.data.di.ViewComponent
import org.pointyware.xyz.core.data.di.ViewModelComponent
import org.pointyware.xyz.core.data.di.WindowComponent
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test

/**
 *
 */
class KoinComponentsTest {

    class AppDep()
    class WindowDep(val appDep: AppDep)
    class ViewModelDep(val windowDep: WindowDep)
    class ViewDep(val viewModelDep: ViewModelDep)

    @BeforeTest
    fun setUp() {
        startKoin {
            modules(
                module {
                    scope<ApplicationComponent> {
                        scopedOf(::AppDep)
                    }
                    scope<WindowComponent> {
                        scopedOf(::WindowDep)
                    }
                    scope<ViewModelComponent> {
                        scopedOf(::ViewModelDep)
                    }
                    scope<ViewComponent> {
                        scopedOf(::ViewDep)
                    }
                }
            )
        }
    }

    @AfterTest
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun nestedComponents() {
        val koin = getKoin()

        val appComponent = ApplicationComponent()
        appComponent.scope.get<AppDep>()

        val windowComponent = WindowComponent(appComponent)
        windowComponent.scope.get<WindowDep>()

        val viewModelComponent = ViewModelComponent(windowComponent)
        viewModelComponent.scope.get<ViewModelDep>()

        val viewComponent = ViewComponent(viewModelComponent)
        viewComponent.scope.get<ViewDep>()
    }
}
