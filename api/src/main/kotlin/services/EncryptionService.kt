package org.pointyware.xyz.api.services

import javax.crypto.Cipher

/**
 * Provides access to encryption and hashing functions.
 */
interface EncryptionService {
    /**
     *  Generates a random salt value.
     */
    fun generateSalt(): Result<String>

    /**
     * Generates a salted hash of the given password using the given salt.
     */
    fun saltedHash(password: String, salt: String): Result<String>

    /**
     * Generates an authorization token with the given resource permissions list. These are
     * implementation dependent, but usually come in a form like "api_bid:read,write;api_ask:read"
     * or abbreviated forms like "bid:rw;ask:r".
     */
    fun generateToken(email: String, resourcePermissions: List<String>): Result<String>
}

/**
 * Implements [EncryptionService] using the Java Cryptography Architecture (JCA) and the
 * Java Cryptography Extension (JCE).
 */
class EncryptionServiceImpl(
    // TODO: inject JVM encryption dependencies
): EncryptionService {

    private val asymmetricCipher = Cipher.getInstance("RSA/ECB/PKCS1Padding")
    private val symmetricCipher = Cipher.getInstance("AES_256/CBC/NoPadding")
    /*
    TODO: load the server pass-key from a secure location
     */

    override fun generateSalt(): Result<String> = runCatching {
        val nonce = ByteArray(16)
        nonce.toHexString()
    }

    override fun saltedHash(password: String, salt: String): Result<String> = runCatching {
        val hashInput = password + salt
        val hash = symmetricCipher.doFinal(hashInput.toByteArray())
        hash.toHexString()
    }

    override fun generateToken(email: String, resourcePermissions: List<String>): Result<String> = runCatching {
        val emailHash = asymmetricCipher.doFinal(email.toByteArray())
        val permissionString = resourcePermissions.joinToString(",")
        val permissionHash = asymmetricCipher.doFinal(permissionString.toByteArray())

        emailHash.toHexString() + "." + permissionHash.toHexString()
    }

    private fun ByteArray.toHexString() = this.joinToString("") { String.format("%02x", it) }
}
