/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.feature.ride.di

import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.pointyware.xyz.feature.ride.viewmodels.RideViewModel

/**
 *
 */
interface RideDependencies {
    fun getRideViewModel(): RideViewModel

}

class KoinRideDependencies: RideDependencies, KoinComponent {
    override fun getRideViewModel(): RideViewModel {
        return get()
    }
}
