package org.pointyware.xyz.api.services

import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertFails
import kotlin.test.assertNotEquals

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
        val hash = serviceUnderTest.saltedHash("password", "salt")

        assertNotEquals("", hash)
    }

    @Test
    fun generate_token_fails_for_unknown_user() {
        assertFails {
            serviceUnderTest.generateToken(email = "some-unknown@some.maiil", resourcePermissions = emptyList())
        }
    }

    @Test
    fun generate_token_passes_for_known_user() {
        val token = serviceUnderTest.generateToken(email = "user_email@some.mail", resourcePermissions = emptyList())

        assertNotEquals("", token)
    }
}
