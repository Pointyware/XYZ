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
        val hash = serviceUnderTest.saltedHash("password").getOrThrow()

        assertNotEquals("", hash)
    }

    @Test
    fun previously_hashed_password_matches_given_password() {
        val password = "password"
        val hash = serviceUnderTest.saltedHash(password).getOrThrow()

        val matches = serviceUnderTest.matches(password, hash).getOrThrow()

        assertTrue(matches)
    }

    @Test
    fun previously_hashed_password_does_not_match_different_password() {
        val password = "password"
        val hash = serviceUnderTest.saltedHash(password).getOrThrow()

        val matches = serviceUnderTest.matches("different-password", hash).getOrThrow()

        assertFalse(matches)
    }

    @Test
    fun generate_token_returns_encrypted_message() {
        val uuid = Uuid.random()
        val token = serviceUnderTest.generateToken(uuid = uuid, resourcePermissions = emptyList()).getOrThrow()

        assertEquals(2, token.split('.').size)
    }
}
