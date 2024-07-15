/*
 * Copyright (c) 2024 Pointyware
 */

package org.pointyware.xyz.core.entities

/**
 *
 */
class Profile(
    val email: String,
    val name: Name,
    val phone: PhoneNumber,
    val gender: Gender,
    val picture: Uri,
) {
}
