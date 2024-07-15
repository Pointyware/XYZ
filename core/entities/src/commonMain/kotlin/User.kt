/*
 * Copyright (c) 2024 Pointyware
 */

package org.pointyware.xyz.core.entities

/**
 *
 */
sealed class User(
    val profile: Profile,
    val authToken: String,
) {
    /**
     *
     */
    class Rider(
        profile: Profile,
        authToken: String,
    ) : User(profile, authToken)

    /**
     *
     */
    class Driver(
        profile: Profile,
        authToken: String,
    ) : User(profile, authToken)

    /**
     *
     */
    class Owner(
        profile: Profile,
        authToken: String,
    ): User(profile, authToken)
}
