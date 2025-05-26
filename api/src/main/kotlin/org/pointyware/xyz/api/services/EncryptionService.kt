package org.pointyware.xyz.api.services

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import javax.crypto.Cipher
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

/**
 * Provides access to encryption and hashing functions.
 */
@OptIn(ExperimentalUuidApi::class)
interface EncryptionService {
    /**
     * Generates a salted hash of the given password using the given salt.
     */
    fun saltedHash(password: String): String

    /**
     *
     */
    fun matches(password: String, passwordHash: String): Boolean

    /**
     * Generates an authorization token with the given resource permissions list. These are
     * implementation dependent, but usually come in a form like "api_bid:read,write;api_ask:read"
     * or abbreviated forms like "bid:rw;ask:r".
     */
    fun generateToken(uuid: Uuid, resourcePermissions: List<String>): String
}

/**
 * Implements [EncryptionService] using the Java Cryptography Architecture (JCA) and the
 * Java Cryptography Extension (JCE) as well as the Spring Security library.
 */
@OptIn(ExperimentalUuidApi::class)
class EncryptionServiceImpl(
    // TODO: inject JVM encryption dependencies
): EncryptionService {

    private val asymmetricCipher = Cipher.getInstance("RSA/ECB/PKCS1Padding")
    private val symmetricCipher = Cipher.getInstance("AES_256/CBC/NoPadding")
    /*
    TODO: load the server pass-key from a secure location
     */

    private val encoder = BCryptPasswordEncoder()

    override fun saltedHash(password: String): String {
        val hash = encoder.encode(password)
        return hash
    }

    override fun generateToken(uuid: Uuid, resourcePermissions: List<String>): String {
        val idHash = asymmetricCipher.doFinal(uuid.toByteArray())
        val permissionString = resourcePermissions.joinToString(",")
        val permissionHash = asymmetricCipher.doFinal(permissionString.toByteArray())

        return idHash.toHexString() + "." + permissionHash.toHexString()
    }

    override fun matches(password: String, passwordHash: String): Boolean {
        return encoder.matches(password, passwordHash)
    }

    private fun ByteArray.toHexString() = this.joinToString("") { String.format("%02x", it) }
}
