package org.pointyware.xyz.shared.home

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/**
 *
 */
interface HomeViewModel {
    val state: StateFlow<HomeUiState>
}

class HomeViewModelImpl: HomeViewModel {
    private val mutableState = MutableStateFlow(HomeUiState())
    override val state: StateFlow<HomeUiState>
        get() = mutableState
}
