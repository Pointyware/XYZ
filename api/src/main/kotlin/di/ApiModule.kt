package org.pointyware.xyz.api.di

import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.pointyware.xyz.api.controllers.AuthController
import org.pointyware.xyz.api.controllers.AuthControllerImpl
import org.pointyware.xyz.api.controllers.OrderController
import org.pointyware.xyz.api.controllers.PaymentsController
import org.pointyware.xyz.api.controllers.PaymentsControllerImpl
import org.pointyware.xyz.api.controllers.OrderControllerImpl
import org.pointyware.xyz.api.databases.AuthDatabase
import org.pointyware.xyz.api.databases.AuthDatabaseImpl
import org.pointyware.xyz.api.databases.PostgresConnectionFactory
import org.pointyware.xyz.api.databases.RideDatabase
import org.pointyware.xyz.api.databases.RideDatabaseImpl
import org.pointyware.xyz.api.services.PaymentsService
import org.pointyware.xyz.api.services.RideServiceImpl
import org.pointyware.xyz.api.services.UserServiceImpl
import org.pointyware.xyz.api.services.RideService
import org.pointyware.xyz.api.services.StripePaymentsService
import org.pointyware.xyz.api.services.UserService
import java.sql.Connection

/**
 *  Provides top-down entry point to entire dependency graph.
 */
fun apiModule() = module {
    includes(
        controllersModule(),
        servicesModule(),
        databasesModule()
    )
}

/**
 * This module provides implementations for all controllers. Controllers depend on services,
 * but those are provided separately by [servicesModule].
 */
fun controllersModule() = module {
    singleOf(::AuthControllerImpl) { bind<AuthController>() }
    singleOf(::OrderControllerImpl) { bind<OrderController>() }
    singleOf(::PaymentsControllerImpl) { bind<PaymentsController>() }
}

/**
 * This module provides implementations for all services, including any framework-specific
 * dependencies, e.g. [postgresModule] is included.
 */
fun servicesModule() = module {
    singleOf(::UserServiceImpl) { bind<UserService>() }
    singleOf(::RideServiceImpl) { bind<RideService>() }
    singleOf(::StripePaymentsService) { bind<PaymentsService>() }
}

/**
 * This module provides implementations for all databases, including any framework-specific
 * dependencies, e.g. [postgresModule] is included.
 */
fun databasesModule() = module {
    singleOf(::AuthDatabaseImpl) { bind<AuthDatabase>() }
    singleOf(::RideDatabaseImpl) { bind<RideDatabase>() }

    includes(
        postgresModule()
    )
}

/**
 * This module provides all basic dependencies for any PostgreSQL-dependent implementations.
 */
fun postgresModule() = module {
    // We don't need to retain the factory
    factoryOf(::PostgresConnectionFactory)

    // We need to retain a single connection, wherever it is requested
    single<Connection> { get<PostgresConnectionFactory>().createConnection() }
}
