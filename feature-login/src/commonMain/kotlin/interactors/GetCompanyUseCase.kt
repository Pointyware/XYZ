/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.feature.login.interactors

import org.pointyware.xyz.core.entities.Uuid
import org.pointyware.xyz.core.entities.business.Company
import org.pointyware.xyz.feature.login.data.CompanyRepository

/**
 * Retrieves a [Company] profile by [Uuid]
 */
class GetCompanyUseCase(
    private val companyRepository: CompanyRepository
) {
    fun invoke(uuid: Uuid): Result<Company> {
        return companyRepository.getCompany(uuid)
    }
}
