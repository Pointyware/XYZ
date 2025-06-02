/*
 * Copyright (c) 2024-2025 Pointyware. Use of this software is governed by the Affero GPL-3.0 license.
 */

package org.pointyware.xyz.feature.login.data

import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

/**
 * Separates authorization information from the profile entity.
 */
@OptIn(ExperimentalUuidApi::class)
interface Authorization {
    /**
     * User email.
     */
    val userId: Uuid

    /**
     * User token.
     */
    val token: String

}
