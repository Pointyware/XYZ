package org.pointyware.xyz.api.services

import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue

class EncryptionServiceTest {

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
        val hash = serviceUnderTest.saltedHash("password", "salt").getOrThrow()

        assertNotEquals("", hash)
    }

    @Test
    fun generate_token_returns_encrypted_message() {
        val token = serviceUnderTest.generateToken(email = "user_email@some.mail", resourcePermissions = emptyList()).getOrThrow()

        assertTrue(token.split('.').size == 2)
    }
}
