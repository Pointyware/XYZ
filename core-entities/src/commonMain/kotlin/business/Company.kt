/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.core.entities.business

import kotlinx.serialization.Serializable
import org.pointyware.xyz.core.entities.profile.PhoneNumber
import org.pointyware.xyz.core.entities.data.Uri

/**
 * A business entity.
 *
 * Etymologically, refers to what you do with your time: "busy-ness", from Old English "bisig", meaning "anxiety" or "care".
 * - https://www.etymonline.com/word/business
 */
@Serializable
sealed interface Business {

}

/**
 * A business owned and operated by a single individual. No special legal status.
 */
@Serializable
data object Individual : Business

/**
 * A legally recognized business entity.
 * LLC, LLP, Corporation, etc.
 * Legal recognition varies by jurisdiction, i.e. federal, state-by-state.
 *
 * - https://www.irs.gov/businesses/small-businesses-self-employed/business-structures
 * - https://oklahoma.gov/business/plan/legal-structure.html
 *
 * Etymologically, refers to a "bread fellow", one with whom you are intimate.
 * - https://www.etymonline.com/word/company
 */
@Serializable
data class Company(
    val name: String,
    val tagline: String,
    val bio: String,
    val banner: Uri,
    val logo: Uri,
    val phone: PhoneNumber,
): Business
