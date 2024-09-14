/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.drive.viewmodels

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.pointyware.xyz.core.entities.business.Rate.Companion.div
import org.pointyware.xyz.core.entities.geo.LengthUnit
import org.pointyware.xyz.core.viewmodels.ViewModel
import org.pointyware.xyz.drive.data.DriverSettingsRepository
import org.pointyware.xyz.drive.entities.DriverRates
import org.pointyware.xyz.drive.interactors.CurrencyInputValidator

/**
 *
 */
class DriverSettingsViewModel(
    private val driverSettingsRepository: DriverSettingsRepository,
    private val currencyInputValidator: CurrencyInputValidator
): ViewModel() {

    private fun getDriverSettingsUiState(): DriverSettingsUiState {
        val rates = driverSettingsRepository.getDriverRates()
        return DriverSettingsUiState(
            maintenanceCost = rates.maintenanceCost.format(),
            pickupCost = rates.pickupCost.format(),
            dropoffCost = rates.dropoffCost.format()
        )
    }

    private val mutableState = MutableStateFlow(getDriverSettingsUiState())
    val state: StateFlow<DriverSettingsUiState> = mutableState.asStateFlow()

    fun onUpdateCostOfMaintenance(input: String) {
        if (currencyInputValidator.validate(input)) {
            mutableState.update {
                it.copy(maintenanceCost = input)
            }
        }
    }

    fun onUpdatePickupPricePerMile(input: String) {
        if (currencyInputValidator.validate(input)) {
            mutableState.update {
                it.copy(pickupCost = input)
            }
        }
    }

    fun onUpdateDropoffPricePerMile(input: String) {
        if (currencyInputValidator.validate(input)) {
            mutableState.update {
                it.copy(dropoffCost = input)
            }
        }
    }

    fun onRevertChanges() {
        mutableState.value = getDriverSettingsUiState()
    }

    fun onSubmitChanges() {
        state.value.let {
            val maintenanceCost = currencyInputValidator.parse(it.maintenanceCost)
            val pickupCost = currencyInputValidator.parse(it.pickupCost)
            val dropoffCost = currencyInputValidator.parse(it.dropoffCost)
            val rates = DriverRates(
                maintenanceCost / LengthUnit.MILES,
                pickupCost / LengthUnit.MILES,
                dropoffCost / LengthUnit.MILES
            )
            driverSettingsRepository.setDriverRates(rates)
        }
    }
}

data class DriverSettingsUiState(
    val maintenanceCost: String,
    val pickupCost: String,
    val dropoffCost: String
)
