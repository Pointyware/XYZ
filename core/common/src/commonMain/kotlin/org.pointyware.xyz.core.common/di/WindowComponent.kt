/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.core.common.di

import org.koin.core.component.KoinScopeComponent
import org.koin.core.scope.Scope

class WindowComponent(private val appComponent: ApplicationComponent): KoinScopeComponent {
    override val scope: Scope by nestedScope(appComponent)

    fun finish() {
        scope.unlink(appComponent.scope)
        scope.close()
    }
}
