/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.core.viewmodels

import org.pointyware.xyz.core.entities.data.Uri
import org.pointyware.xyz.core.entities.ride.Rating
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

/**
 * A brief profile UI state. For more detail see [ProfileUiState].
 */
@OptIn(ExperimentalUuidApi::class)
interface BriefProfileUiState {
    val id: Uuid
    val image: Uri
    val name: String
    val rating: Rating
}
