/*
 * Copyright (c) 2024-2025 Pointyware. Use of this software is governed by the Affero GPL-3.0 license.
 */

package org.pointyware.xyz.core.viewmodels

import org.koin.core.component.KoinScopeComponent
import org.koin.core.component.newScope
import org.koin.core.scope.Scope

/**
 * Extends basic ViewModel concepts to enable koin dependency injection in a scoped context.
 */
abstract class KoinViewModel(): ViewModel(), KoinScopeComponent {

    override val scope: Scope by newScope()

    override fun dispose() {
        super.dispose()
        scope.close()
    }
}
