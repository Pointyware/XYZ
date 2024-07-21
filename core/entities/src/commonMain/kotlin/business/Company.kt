/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.core.entities.business

import org.pointyware.xyz.core.entities.profile.PhoneNumber
import org.pointyware.xyz.core.entities.data.Uri

/**
 */
interface Company {
    val name: String
    val tagline: String
    val bio: String
    val banner: Uri
    val logo: Uri
    val phone: PhoneNumber
}
