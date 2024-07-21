/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.core.viewmodels

import org.pointyware.xyz.core.entities.Rating
import org.pointyware.xyz.core.entities.Uri
import org.pointyware.xyz.core.entities.Uuid

/**
 * A brief profile UI state. For more detail see [ProfileUiState].
 */
interface BriefProfileUiState {
    val id: Uuid
    val image: Uri
    val name: String
    val rating: Rating
}
