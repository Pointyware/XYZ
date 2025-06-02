/*
 * Copyright (c) 2024-2025 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.core.interactors.di

import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import org.pointyware.xyz.core.interactors.business.CurrencyInputValidator

/**
 *
 */
fun coreInteractorsModule() = module {
    factoryOf(::CurrencyInputValidator)
}
