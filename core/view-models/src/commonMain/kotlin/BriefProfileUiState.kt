/*
 * Copyright (c) 2024 Pointyware
 */

package org.pointyware.xyz.core.viewmodels

import org.pointyware.xyz.core.entities.Rating
import org.pointyware.xyz.core.entities.Uri

/**
 * A brief profile UI state. For more detail see [ProfileUiState].
 */
interface BriefProfileUiState {
    val image: Uri
    val name: String
    val rating: Rating
}
