/*
 * Copyright (c) 2024-2025 Pointyware. Use of this software is governed by the Affero GPL-3.0 license.
 */

package org.pointyware.xyz.feature.login.interactors

import kotlin.uuid.Uuid
import org.pointyware.xyz.core.entities.business.Company
import org.pointyware.xyz.feature.login.data.CompanyRepository
import kotlin.uuid.ExperimentalUuidApi

/**
 * Retrieves a [Company] profile by [Uuid]
 */
@OptIn(ExperimentalUuidApi::class)
class GetCompanyUseCase(
    private val companyRepository: CompanyRepository
) {
    fun invoke(uuid: Uuid): Result<Company> {
        return companyRepository.getCompany(uuid)
    }
}
