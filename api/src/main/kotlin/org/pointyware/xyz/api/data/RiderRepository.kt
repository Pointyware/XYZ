package org.pointyware.xyz.api.data

import java.awt.geom.Point2D
import java.sql.Connection

/**
 * Proposed organization - the database is the top-level entry point to the database. It provides
 * access to several data-access objects (DAOs) that are used to interact with specific tables in the
 * database.
 *
 */
interface RiderRepository {
    /**
     * Exposes the profiles data-access object (DAO) for interacting with the rider profiles
     * table in this database.
     */
    val profiles: ProfileDao

    /**
     * Exposes the requests data-access object (DAO) for interacting with the ride requests
     * table in this database.
     */
    val requests: RequestDao
}

/**
 * A data-access object (DAO) for rider profiles. This interface defines the CRUD methods
 * specific to profiles.
 */
interface ProfileDao {

    /**
     * Represents ever column in the rider profile table.
     * @param profileId The uuid of the profile.
     * @param userId The uuid of the user.
     * @param firstName
     * @param middleName
     * @param lastName
     * @param phone
     * @param rating 0 indicates no rating, 1-5 indicates a rating.
     * @param createdAt
     */
    data class CompleteRow(
        val profileId: String,
        val userId: String,
        val firstName: String,
        val middleName: String,
        val lastName: String,
        val phone: String,
        val rating: Float,
        val createdAt: String
    )

    /**
     * Creates a new rider profile in the database for the given [userId].
     */
    fun createProfile(
        userId: String,
        firstName: String,
        middleName: String,
        lastName: String,
        email: String,
        phone: String,
        address: String,
    ): CompleteRow
}

interface RequestDao {
    /**
     * Represents ever column in the ride request table.
     * @param requestId The uuid of the request.
     * @param riderId The uuid of the rider.
     * @param startLocation The starting location of the ride.
     * @param endLocation The ending location of the ride.
     * @param status The status of the ride request.
     */
    data class CompleteRow(
        val requestId: String,
        val riderId: String,
        val startLocation: String,
        val endLocation: String,
        val status: String,
    )

    /**
     *
     */
    fun requestRide(
        riderId: String,
        startLocation: Point2D.Double,
        endLocation: Point2D.Double,
        rate: Long,
    ): CompleteRow
}

/**
 * A data-access object (DAO) for rides. This interface defines the methods that can be used to
 * interact with the rides table in the database.
 */
interface RideDao {
    suspend fun createRide(ride: RideDto)
    suspend fun getRideById(id: String): RideDto
    suspend fun updateRide(ride: RideDto)
    suspend fun deleteRide(id: String)
}

/**
 * Data transfer object (DTO) for rides. This class represents a ride in the database and is used
 * to transfer data between the database and the application.
 */
data class RideDto(
    val id: String,
    val startLocation: String,
    val endLocation: String,
    val cost: Long,
    val status: String,
    val driverId: String,
    val riderId: String,
)

/**
 *  https://jdbc.postgresql.org/
 */
class RiderRepositoryImpl(
    private val connectionProvider: () -> Connection
) : RiderRepository {

    private val connection by lazy {
        connectionProvider.invoke()
    }

    override val profiles: ProfileDao
        = object : ProfileDao {
            override fun createProfile(
                userId: String,
                firstName: String,
                middleName: String,
                lastName: String,
                email: String,
                phone: String,
                address: String
            ): ProfileDao.CompleteRow {
                TODO("Not yet implemented")
            }
        }
    override val requests: RequestDao
        = object : RequestDao {
            override fun requestRide(
                riderId: String,
                startLocation: Point2D.Double,
                endLocation: Point2D.Double,
                rate: Long
            ): RequestDao.CompleteRow {
                TODO("Not yet implemented")
            }
        }
}
