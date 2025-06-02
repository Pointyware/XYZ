package org.pointyware.xyz.api.dtos.auth

import kotlinx.serialization.Serializable
import org.pointyware.xyz.api.dtos.ServerError

@Serializable
class AuthorizationError(

): ServerError("Session is invalid or expired.")
