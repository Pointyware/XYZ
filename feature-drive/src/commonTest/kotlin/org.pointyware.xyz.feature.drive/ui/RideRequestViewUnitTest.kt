/*
 * Copyright (c) 2024-2025 Pointyware. Use of this software is governed by the Affero GPL-3.0 license.
 */

package org.pointyware.xyz.feature.drive.ui

import androidx.compose.ui.test.ComposeUiTest
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.runComposeUiTest
import org.pointyware.xyz.core.entities.business.Rate.Companion.div
import org.pointyware.xyz.core.entities.business.dollarCents
import org.pointyware.xyz.core.entities.geo.LengthUnit
import org.pointyware.xyz.core.entities.geo.Location
import org.pointyware.xyz.core.entities.geo.Route
import org.pointyware.xyz.core.entities.geo.miles
import org.pointyware.xyz.ui.design.XyzTheme
import org.pointyware.xyz.drive.entities.DriverRates
import org.pointyware.xyz.drive.ui.RideRequestView
import org.pointyware.xyz.drive.viewmodels.RideRequestUiStateImpl
import kotlin.test.Test
import kotlin.time.Duration.Companion.minutes
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

/**
 *
 */
@OptIn(ExperimentalTestApi::class, ExperimentalUuidApi::class)
class RideRequestViewUnitTest {


    private fun ComposeUiTest.contentUnderTest() {
        val missionOfHopeLocation = Location(
            36.103334, -97.051601,
            name = "Mission of Hope",
            address = "1804 S Perkins Rd",
        )
        val walmartLocation = Location(
            36.124656, -97.048823,
            name = "Walmart",
            address = "111 N Perkins Rd",
        )
        val pickupDistance = 1.1.miles()
        val pickupDuration = 4.minutes
        val routeDistance = 1.6.miles()
        val routeDuration = 5.minutes
        setContent {
            XyzTheme(
            ) {
                RideRequestView(
                    state = RideRequestUiStateImpl(
                        requestId = Uuid.random(),
                        riderName = "John",
                        route = Route(
                            start = missionOfHopeLocation,
                            intermediates = emptyList(),
                            end = walmartLocation,
                            distance = routeDistance,
                            duration = routeDuration
                        ),
                        riderServiceRate = 100L.dollarCents() / LengthUnit.MILES,
                        pickupDistance = pickupDistance,
                        driverRates = DriverRates(
                            16L.dollarCents() / LengthUnit.MILES,
                            0L.dollarCents() / LengthUnit.MILES,
                            83L.dollarCents() / LengthUnit.MILES
                        )
                    ),
                    onAccept = {},
                    onReject = {},
                )
            }
        }
    }

    @Test
    fun `request view displays rider name`() = runComposeUiTest {

        contentUnderTest()

        onNodeWithContentDescription("Rider Name", substring = true)
            .assertExists()
            .assertTextContains("John", substring = true)
    }

    @Test
    fun `request view displays pickup location + distance + cost`() = runComposeUiTest {

        contentUnderTest()

        onNodeWithContentDescription("Pickup Location", substring = true)
            .assertExists()
            .assertTextContains("Mission of Hope", substring = true)
        onNodeWithContentDescription("Pickup Metrics", substring = true)
            .assertExists()
            .assertTextContains("1.10 mi @ $0.00/mi", substring = true)
            .assertTextContains("$0.00 - $0.17", substring = true)
    }

    @Test
    fun `request view displays dropoff location + distance + cost`() = runComposeUiTest {

        contentUnderTest()

        onNodeWithContentDescription("Dropoff Location", substring = true)
            .assertExists()
            .assertTextContains("Walmart", substring = true)
        onNodeWithContentDescription("Route Metrics", substring = true)
            .assertExists()
            .assertTextContains("1.60 mi @ $0.83/mi", substring = true)
            .assertTextContains("$1.32 - $0.25", substring = true)
    }
}
