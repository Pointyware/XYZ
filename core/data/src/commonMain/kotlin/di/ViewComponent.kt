/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.core.data.di

import org.koin.core.component.KoinScopeComponent
import org.koin.core.component.createScope
import org.koin.core.scope.Scope

class ViewComponent(private val viewModelComponent: ViewModelComponent): KoinScopeComponent {
    override val scope: Scope by lazy {
        createScope(this).also {
            it.linkTo(viewModelComponent.scope)
        }
    }
}
