/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
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
