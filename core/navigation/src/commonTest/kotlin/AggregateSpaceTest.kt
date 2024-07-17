/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.core.navigation

import kotlin.test.Test
import kotlin.test.assertTrue
import kotlin.test.assertFalse

class AggregateSpaceTest {

    @Test
    fun `test contains`() {
        val aggregateSpace = AggregateSpace<String>()
        val location = "TestLocation"
        // Initially, the space should not contain the location
        assertFalse(aggregateSpace.contains(location))

        // After adding the location, it should be contained in the space
        aggregateSpace.add(location)
        assertTrue(aggregateSpace.contains(location))
    }

    @Test
    fun `test add`() {
        val aggregateSpace = AggregateSpace<String>()
        val location = "TestLocation"

        // After adding the location, it should be contained in the space
        aggregateSpace.add(location)
        assertTrue(aggregateSpace.contains(location))
    }

    @Test
    fun `test remove`() {
        val aggregateSpace = AggregateSpace<String>()
        val location = "TestLocation"

        // Add the location and then remove it
        aggregateSpace.add(location)
        aggregateSpace.remove(location)
        // After removal, the location should not be contained in the space
        assertFalse(aggregateSpace.contains(location))
    }
}
