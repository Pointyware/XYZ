/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.feature.login.data

import org.pointyware.xyz.core.entities.Uuid
import org.pointyware.xyz.core.entities.business.Company
import org.pointyware.xyz.feature.login.local.CompanyCache
import org.pointyware.xyz.feature.login.remote.CompanyService

data class CompanyNotFoundException(val uuid: Uuid): Exception("Company not found with uuid: $uuid")

/**
 */
interface CompanyRepository {
    fun getCompany(uuid: Uuid): Result<Company>
}

/**
 *
 */
class CompanyRepositoryImpl(
    private val companyCache: CompanyCache,
    private val companyService: CompanyService,
): CompanyRepository {
    override fun getCompany(uuid: Uuid): Result<Company> {
        return companyCache.getCompany(uuid)
            .onFailure {
                companyService.getCompany(uuid)
                    .onSuccess { companyCache.saveCompany(it) }
            }
    }
}

class TestCompanyRepository(
    // TODO: create file persistence for testing
    private val companies: MutableMap<Uuid, Company> = mutableMapOf()
): CompanyRepository {
    override fun getCompany(uuid: Uuid): Result<Company> {
        return companies[uuid]?.let { Result.success(it) } ?: Result.failure(CompanyNotFoundException(uuid))
    }
}
