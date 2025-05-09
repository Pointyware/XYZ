package org.pointyware.xyz.api.di

import org.koin.dsl.module
import org.pointyware.xyz.api.databases.PostgresConnectionFactory
import org.pointyware.xyz.api.services.PostgresUserService
import org.pointyware.xyz.api.services.UserService

fun apiModule() = module {
    single { PostgresConnectionFactory() }
    single { get<PostgresConnectionFactory>().createConnection() }
    single<UserService> { PostgresUserService(get()) }
}
