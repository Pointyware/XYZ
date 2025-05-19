package org.pointyware.xyz.api.services

import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertNotEquals
import org.pointyware.xyz.api.data.AuthRepository
import org.pointyware.xyz.api.data.AuthRepositoryImpl
import org.pointyware.xyz.api.data.PostgresConnectionFactory
import java.sql.Connection
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test

class AuthServiceUnitTest {

    private lateinit var connection: Connection
    private lateinit var authRepository: AuthRepository
    private lateinit var encryptionService: EncryptionService
    private lateinit var unitUnderTest: UserService

    @BeforeTest
    fun setUp() {
        connection = PostgresConnectionFactory().createConnection()
        authRepository = AuthRepositoryImpl(
            connectionProvider = { connection },
        )
        encryptionService = EncryptionServiceImpl()
        unitUnderTest = UserServiceImpl(encryptionService, authRepository)
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
