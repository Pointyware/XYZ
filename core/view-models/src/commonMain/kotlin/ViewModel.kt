/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.core.viewmodels

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel

/**
 * Defines base behavior for all view models.
 */
abstract class ViewModel {

    /**
     * The scope for the view model. The default uses the main dispatcher and a supervisor job.
     */
    protected val viewModelScope: CoroutineScope by lazy {
        CoroutineScope(Dispatchers.Main + SupervisorJob())
    }

    open fun dispose() {
        viewModelScope.coroutineContext.cancel()
    }
}
