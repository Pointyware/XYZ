package org.pointyware.xyz.api.services

import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertNotEquals
import org.pointyware.xyz.api.databases.PostgresConnectionFactory
import java.sql.Connection
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test

class AuthServiceUnitTest {

    private lateinit var connection: Connection
    private lateinit var encryptionService: EncryptionService
    private lateinit var unitUnderTest: UserService

    @BeforeTest
    fun setUp() {
        connection = PostgresConnectionFactory().createConnection()
        encryptionService = EncryptionServiceImpl()
        unitUnderTest = PostgresUserService(encryptionService, connection)
    }

    @AfterTest
    fun tearDown() {

    }

    @Test
    fun create_user_should_return_authorization() = runTest {
        /*
        Given a user with a valid email and password
         */
        val email = "some-user@some.mail"
        val password = "some-password"

        /*
        When the user is created
         */
        val result = unitUnderTest.createUser(email, password)

        /*
        Then the result should be a success
         */
        assertNotEquals("", result.email)
        assertNotEquals("", result.token)
    }
}
