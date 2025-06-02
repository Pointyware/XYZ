/*
 * Copyright (c) 2025 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.api.data

import java.sql.Connection

/**
 * This is the top-level entry point to common (shared) data models.
 *
 * This includes rides, by may also include the market models later.
 */
interface CommonRepository {
    /**
     * Exposes the rides data-access object (DAO) for interacting with the rides table in this
     * database.
     */
    val rides: RideDao
}

/**
 *
 */
class CommonRepositoryImpl(
    private val connection: Connection
) : CommonRepository {

    override val rides: RideDao
        get() = object : RideDao {
            override suspend fun createRide(ride: RideDto) {
                connection.prepareStatement(
                    "INSERT INTO rides (id, start_location, end_location, cost, status) VALUES (?, ?, ?, ?, ?)"
                ).apply {
                    setString(1, ride.id)
                    setString(2, ride.startLocation)
                    setString(3, ride.endLocation)
                    setLong(4, ride.cost)
                    setString(5, ride.status)
                }.executeUpdate()
            }

            override suspend fun getRideById(id: String): RideDto {
                connection.prepareStatement(
                    "SELECT id, start_location, end_location, cost, status, rider_id, driver_id FROM rides WHERE id = ?"
                ).apply {
                    setString(1, id)
                }.executeQuery().use { resultSet ->
                    if (resultSet.next()) {
                        return RideDto(
                            id = resultSet.getString("id"),
                            startLocation = resultSet.getString("start_location"),
                            endLocation = resultSet.getString("end_location"),
                            cost = resultSet.getLong("cost"),
                            status = resultSet.getString("status"),
                            riderId = resultSet.getString("rider_id"),
                            driverId = resultSet.getString("driver_id"),
                        )
                    } else {
                        throw NoSuchElementException("No ride found with id: $id")
                    }
                }
            }

            override suspend fun updateRide(ride: RideDto) {
                connection.prepareStatement(
                    "UPDATE rides SET start_location = ?, end_location = ?, cost = ?, status = ? WHERE id = ?"
                ).apply {
                    setString(1, ride.startLocation)
                    setString(2, ride.endLocation)
                    setLong(3, ride.cost)
                    setString(4, ride.status)
                    setString(5, ride.id)
                }.executeUpdate()
            }

            override suspend fun deleteRide(id: String) {
                connection.prepareStatement(
                    "DELETE FROM rides WHERE id = ?"
                ).apply {
                    setString(1, id)
                }.executeUpdate()
            }
        }
}
