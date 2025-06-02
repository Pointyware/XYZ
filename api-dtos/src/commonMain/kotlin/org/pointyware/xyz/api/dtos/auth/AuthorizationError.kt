/*
 * Copyright (c) 2025 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.api.dtos.auth

import kotlinx.serialization.Serializable
import org.pointyware.xyz.api.dtos.ServerError

@Serializable
class AuthorizationError(

): ServerError("Session is invalid or expired.")
