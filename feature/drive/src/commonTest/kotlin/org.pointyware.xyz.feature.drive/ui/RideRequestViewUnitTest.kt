/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.feature.drive.ui

import androidx.compose.ui.test.ComposeUiTest
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.runComposeUiTest
import org.pointyware.xyz.core.entities.Uuid
import org.pointyware.xyz.core.entities.business.Rate.Companion.div
import org.pointyware.xyz.core.entities.business.dollarCents
import org.pointyware.xyz.core.entities.geo.LengthUnit
import org.pointyware.xyz.core.entities.geo.Location
import org.pointyware.xyz.core.entities.geo.Route
import org.pointyware.xyz.core.entities.geo.miles
import org.pointyware.xyz.core.ui.design.XyzTheme
import org.pointyware.xyz.core.ui.di.EmptyTestUiDependencies
import org.pointyware.xyz.drive.entities.DriverRates
import org.pointyware.xyz.drive.ui.RideRequestView
import org.pointyware.xyz.drive.viewmodels.RideRequestUiState
import org.pointyware.xyz.drive.viewmodels.RideRequestUiStateImpl
import kotlin.test.Test
import kotlin.time.Duration.Companion.minutes

/**
 *
 */
@OptIn(ExperimentalTestApi::class)
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
                uiDependencies = EmptyTestUiDependencies()
            ) {
                RideRequestView(
                    state = RideRequestUiStateImpl(
                        requestId = Uuid.v4(),
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
        onNodeWithContentDescription("Pickup Distance", substring = true)
            .assertExists()
            .assertTextContains("1.1 miles", substring = true)
        onNodeWithContentDescription("Pickup Rate", substring = true)
            .assertExists()
            .assertTextContains("$0.00/mi", substring = true)
        onNodeWithContentDescription("Pickup Cost", substring = true)
            .assertExists()
            .assertTextContains("$0.00", substring = true)
        onNodeWithContentDescription("Pickup Maintenance Cost", substring = true)
            .assertExists()
            .assertTextContains("$0.18", substring = true)
    }

    @Test
    fun `request view displays dropoff location + distance + cost`() = runComposeUiTest {

        contentUnderTest()

        onNodeWithContentDescription("Dropoff Location", substring = true)
            .assertExists()
            .assertTextContains("Walmart", substring = true)
        onNodeWithContentDescription("Route Distance", substring = true)
            .assertExists()
            .assertTextContains("1.1 miles", substring = true)
        onNodeWithContentDescription("Route Rate", substring = true)
            .assertExists()
            .assertTextContains("$0.83/mi", substring = true)
        onNodeWithContentDescription("Route Cost", substring = true)
            .assertExists()
            .assertTextContains("$1.33", substring = true)
        onNodeWithContentDescription("Route Maintenance Cost", substring = true)
            .assertExists()
            .assertTextContains("$0.88", substring = true)
    }
}
