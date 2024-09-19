/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.core.common.di

import org.koin.core.component.KoinScopeComponent
import org.koin.core.component.createScope
import org.koin.core.scope.Scope

class ViewModelComponent(private val windowComponent: WindowComponent): KoinScopeComponent {
    override val scope: Scope by nestedScope(windowComponent)

    fun finish() {
        scope.unlink(windowComponent.scope)
        scope.close()
    }
}
