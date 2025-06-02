/*
 * Copyright (c) 2024-2025 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.core.common.di

import org.koin.core.component.KoinScopeComponent
import org.koin.core.component.createScope
import org.koin.core.scope.Scope

/**
 * Create a nested scope for the component. If a parent is provided, the scope will be linked to the parent.
 */
fun KoinScopeComponent.nestedScope(parent: KoinScopeComponent? = null): Lazy<Scope> {
    return lazy {
        createScope(this).also {
            parent?.let { parent ->
                it.linkTo(parent.scope)
            }
        }
    }
}
