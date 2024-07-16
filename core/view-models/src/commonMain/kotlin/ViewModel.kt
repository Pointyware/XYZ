/*
 * Copyright (c) 2024 Pointyware
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

    fun dispose() {
        viewModelScope.coroutineContext.cancel()
    }
}