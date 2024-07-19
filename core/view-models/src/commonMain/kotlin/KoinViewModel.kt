/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.core.viewmodels

import org.koin.core.component.KoinScopeComponent
import org.koin.core.component.newScope
import org.koin.core.scope.Scope
import org.koin.core.scope.ScopeCallback

/**
 * Extends basic ViewModel concepts to enable koin dependency injection in a scoped context.
 */
class KoinViewModel(): ViewModel(), KoinScopeComponent {

    override val scope: Scope by newScope()

    init {
        scope.registerCallback(object : ScopeCallback {
            override fun onScopeClose(scope: Scope) {
                dispose()
            }
        })
    }
}
