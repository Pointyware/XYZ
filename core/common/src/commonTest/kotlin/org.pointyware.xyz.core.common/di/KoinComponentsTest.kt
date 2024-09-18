/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.core.common.di

import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.module.dsl.scopedOf
import org.koin.dsl.module
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails

/**
 * Tests the ability to create and manage nested components and scopes with Koin
 */
class KoinComponentsTest {

    /**
     * A dependency of the Application
     */
    class AppDep()

    /**
     * A dependency of the Window
     */
    class WindowDep(val appDep: AppDep)

    /**
     * A dependency of the ViewModel
     */
    class ViewModelDep(val windowDep: WindowDep)

    /**
     * A dependency of the View
     */
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
    fun `application scope`() {
        val appComponent = ApplicationComponent()
        assertEquals(appComponent, appComponent.scope.get<ApplicationComponent>(),
            "ApplicationComponent should be available in its own scope")
        val appDep = appComponent.scope.get<AppDep>()
        assertEquals(AppDep::class, appDep::class)

        assertFails("WindowDep should not be available in the ApplicationComponent") {
            appComponent.scope.get<WindowDep>()
        }
    }

    @Test
    fun `window scope within application scope`() {
        val appComponent = ApplicationComponent()
        val appDep = appComponent.scope.get<AppDep>()
        val windowComponent = WindowComponent(appComponent)
        assertEquals(windowComponent, windowComponent.scope.get<WindowComponent>(),
            "WindowComponent should be available in its own scope")
        val windowDep = windowComponent.scope.get<WindowDep>()
        assertEquals(WindowDep::class, windowDep::class)
        val parent = windowComponent.scope.get<ApplicationComponent>()
        assertEquals(appComponent, parent, "ApplicationComponent should be available in the Window Scope")

        val appDepFromWindow = windowComponent.scope.get<AppDep>()
        assertEquals(appDep, appDepFromWindow, "AppDep should be the same instance in Window Scope")
        assertFails("WindowDep should not be available in the ApplicationComponent") {
            appComponent.scope.get<WindowDep>()
        }
    }

    @Test
    fun `view model scope within window scope`() {
        val appComponent = ApplicationComponent()
        val appDep = appComponent.scope.get<AppDep>()
        val windowComponent = WindowComponent(appComponent)
        val windowDep = windowComponent.scope.get<WindowDep>()
        val viewModelComponent = ViewModelComponent(windowComponent)
        assertEquals(viewModelComponent, viewModelComponent.scope.get<ViewModelComponent>(),
            "ViewModelComponent should be available in its own scope")
        val viewModelDep = viewModelComponent.scope.get<ViewModelDep>()
        assertEquals(ViewModelDep::class, viewModelDep::class)
        val parent = viewModelComponent.scope.get<WindowComponent>()
        assertEquals(windowComponent, parent, "WindowComponent should be available in the ViewModel Scope")

        val appDepFromViewModel = viewModelComponent.scope.get<AppDep>()
        assertEquals(appDep, appDepFromViewModel, "AppDep should be the same instance in ViewModel Scope")
        assertFails("ViewModelDep should not be available in the ApplicationComponent") {
            appComponent.scope.get<ViewModelDep>()
        }

        val windowDepFromViewModel = viewModelComponent.scope.get<WindowDep>()
        assertEquals(windowDep, windowDepFromViewModel, "ViewModelDep should be the same instance in ViewModel Scope")
        assertFails("ViewModelDep should not be available in the WindowComponent") {
            windowComponent.scope.get<ViewModelDep>()
        }
    }

    @Test
    fun `view scope within view model scope`() {
        val appComponent = ApplicationComponent()
        val appDep = appComponent.scope.get<AppDep>()
        val windowComponent = WindowComponent(appComponent)
        val windowDep = windowComponent.scope.get<WindowDep>()
        val viewModelComponent = ViewModelComponent(windowComponent)
        val viewModelDep = viewModelComponent.scope.get<ViewModelDep>()
        val viewComponent = ViewComponent(viewModelComponent)
        assertEquals(viewComponent, viewComponent.scope.get<ViewComponent>(),
            "ViewComponent should be available in its own scope")
        val viewDep = viewComponent.scope.get<ViewDep>()
        assertEquals(ViewDep::class, viewDep::class)
        val parent = viewComponent.scope.get<ViewModelComponent>()
        assertEquals(viewModelComponent, parent, "ViewModelComponent should be available in the View Scope")

        val appDepFromView = viewComponent.scope.get<AppDep>()
        assertEquals(appDep, appDepFromView, "AppDep should be the same instance in View Scope")
        assertFails("ViewDep should not be available in the ApplicationComponent") {
            appComponent.scope.get<ViewDep>()
        }
        val windowDepFromView = viewComponent.scope.get<WindowDep>()
        assertEquals(windowDep, windowDepFromView, "WindowDep should be the same instance in View Scope")
        assertFails("ViewDep should not be available in the WindowComponent") {
            windowComponent.scope.get<ViewDep>()
        }
        val viewModelDepFromView = viewComponent.scope.get<ViewModelDep>()
        assertEquals(viewModelDep, viewModelDepFromView, "ViewModelDep should be the same instance in View Scope")
        assertFails("ViewDep should not be available in the ViewModelComponent") {
            viewModelComponent.scope.get<ViewDep>()
        }
    }
}
