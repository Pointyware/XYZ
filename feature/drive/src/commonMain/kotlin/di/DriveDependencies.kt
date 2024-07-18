/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.drive.di

import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.pointyware.xyz.drive.viewmodels.DriveViewModel

/**
 *
 */
interface DriveDependencies {
    fun getDriveViewModel(): DriveViewModel
}

class KoinDriveDependencies: DriveDependencies, KoinComponent {
    override fun getDriveViewModel(): DriveViewModel {
        return get()
    }
}
