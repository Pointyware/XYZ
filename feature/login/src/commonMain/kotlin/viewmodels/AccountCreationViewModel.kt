/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.feature.login.viewmodels

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.pointyware.xyz.core.viewmodels.KoinViewModel

/**
 *
 */
class AccountCreationViewModel: KoinViewModel() {
    private val mutableCreationStep = MutableStateFlow(AccountCreationStep.Role)
    val creationStep: StateFlow<AccountCreationStep> get() = mutableCreationStep
}

enum class AccountCreationStep {
    Role,
    Driver,
    Rider,
    Confirm
}
