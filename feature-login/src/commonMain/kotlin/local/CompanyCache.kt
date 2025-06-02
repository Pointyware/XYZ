/*
 * Copyright (c) 2024-2025 Pointyware. Use of this software is governed by the Affero GPL-3.0 license.
 */

package org.pointyware.xyz.feature.login.local

import kotlin.uuid.Uuid
import org.pointyware.xyz.core.entities.business.Company
import kotlin.uuid.ExperimentalUuidApi

/**
 *
 */
@OptIn(ExperimentalUuidApi::class)
class CompanyCache(

) {
    fun getCompany(uuid: Uuid): Result<Company> {
        TODO("Not yet implemented")
    }

    fun saveCompany(company: Company) {

    }

}
