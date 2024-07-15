/*
 * Copyright (c) 2024 Pointyware
 */

package org.pointyware.xyz.core.ui.di

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.pointyware.xyz.core.ui.ui.LoadingViewResources

/**
 */
interface UiDependencies {
    val loadingViewResources: LoadingViewResources
}

class KoinUiDependencies : UiDependencies, KoinComponent {
    override val loadingViewResources: LoadingViewResources
        get() = get()
}
