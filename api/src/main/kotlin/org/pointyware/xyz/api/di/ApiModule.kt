package org.pointyware.xyz.api.di

import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.pointyware.xyz.api.BuildConfig
import org.pointyware.xyz.api.controllers.AuthController
import org.pointyware.xyz.api.controllers.AuthControllerImpl
import org.pointyware.xyz.api.controllers.OrderController
import org.pointyware.xyz.api.controllers.PaymentsController
import org.pointyware.xyz.api.controllers.PaymentsControllerImpl
import org.pointyware.xyz.api.controllers.OrderControllerImpl
import org.pointyware.xyz.api.controllers.RideController
import org.pointyware.xyz.api.controllers.RideControllerImpl
import org.pointyware.xyz.api.data.AuthRepository
import org.pointyware.xyz.api.data.AuthRepositoryImpl
import org.pointyware.xyz.api.data.PooledConnectionFactory
import org.pointyware.xyz.api.data.PostgresConnectionFactory
import org.pointyware.xyz.api.data.PostgresPooledConnectionFactory
import org.pointyware.xyz.api.data.RiderRepository
import org.pointyware.xyz.api.data.RiderRepositoryImpl
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
    singleOf(::RideControllerImpl) { bind<RideController>() }
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
    singleOf(::AuthRepositoryImpl) { bind<AuthRepository>() }
    singleOf(::RiderRepositoryImpl) { bind<RiderRepository>() }

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

    single<()->Connection> {
        { get<PostgresConnectionFactory>().createConnection() }
    }

    single<PooledConnectionFactory>(qualifier = named("database_xyz")) {
        PostgresPooledConnectionFactory(
            host = BuildConfig.POSTGRES_HOST,
            port = BuildConfig.POSTGRES_PORT.toInt(),
            db = BuildConfig.POSTGRES_DB
        )
    }
}
