package org.pointyware.xyz.shared.home

import org.pointyware.xyz.core.common.Mapper

/**
 */
object HomeUiStateMapper: Mapper<HomeUiState, HomeScreenState> {
    override fun map(input: HomeUiState): HomeScreenState {
        return HomeScreenState(
            false,
            null,
        )
    }
}
