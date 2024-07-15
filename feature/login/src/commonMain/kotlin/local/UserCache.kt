package org.pointyware.xyz.feature.login.local

import org.pointyware.xyz.core.entities.User

/**
 *
 */
interface UserCache {
    fun saveUser(it: User)
    fun dropUser(email: String)
}

class UserCacheImpl(

): UserCache {

    override fun saveUser(it: User) {
        TODO("Not yet implemented")
    }

    override fun dropUser(email: String) {
        TODO("Not yet implemented")
    }
}
