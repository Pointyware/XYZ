/*
 * Copyright (c) 2025 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.api.services

import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
class EncryptionServiceUnitTest {

    private lateinit var serviceUnderTest: EncryptionService
    @BeforeTest
    fun setUp() {
        serviceUnderTest = EncryptionServiceImpl()
    }

    @AfterTest
    fun tearDown() {

    }

    @Test
    fun salted_password() {
        val hash = serviceUnderTest.saltedHash("password")

        assertNotEquals("", hash)
    }

    @Test
    fun previously_hashed_password_matches_given_password() {
        val password = "password"
        val hash = serviceUnderTest.saltedHash(password)

        val matches = serviceUnderTest.matches(password, hash)

        assertTrue(matches)
    }

    @Test
    fun previously_hashed_password_does_not_match_different_password() {
        val password = "password"
        val hash = serviceUnderTest.saltedHash(password)

        val matches = serviceUnderTest.matches("different-password", hash)

        assertFalse(matches)
    }

    @Test
    fun generate_token_returns_encrypted_message() {
        val uuid = Uuid.random()
        val token = serviceUnderTest.generateToken(uuid = uuid, resourcePermissions = emptyList())

        assertEquals(2, token.split('.').size)
    }
}
