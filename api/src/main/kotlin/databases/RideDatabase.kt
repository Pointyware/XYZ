package org.pointyware.xyz.api.databases

/**
 * Proposed organization - the database is the top-level entry point to the database. It provides
 * access to several data-access objects (DAOs) that are used to interact with specific tables in the
 * database.
 *
 */
interface RideDatabase {

    val rides: RideDao
}

interface RideDao {
    suspend fun createRide(ride: RideDto)
    suspend fun getRideById(id: String): RideDto
    suspend fun updateRide(ride: RideDto)
    suspend fun deleteRide(id: String)
}

data class RideDto(
    val id: String,
    val startLocation: String,
    val endLocation: String,
    val cost: Long,
    val status: String
)
