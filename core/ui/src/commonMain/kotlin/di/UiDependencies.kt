/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.core.ui.di

import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.pointyware.xyz.core.ui.components.LoadingViewResources
import org.pointyware.xyz.core.ui.design.Resources

/**
 */
interface UiDependencies {
    val resources: Resources
}

class KoinUiDependencies : UiDependencies, KoinComponent {
    override val resources: Resources = get()
}
