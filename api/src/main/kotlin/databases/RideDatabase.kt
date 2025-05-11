package org.pointyware.xyz.api.databases

/**
 * Proposed organization - the database is the top-level entry point to the database. It provides
 * access to several data-access objects (DAOs) that are used to interact with specific tables in the
 * database.
 *
 */
interface RideDatabase {
    /**
     * Exposes the rides data-access object (DAO) for interacting with the rides table in this
     * database.
     */
    val rides: RideDao
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
    val status: String
)

/**
 *  https://jdbc.postgresql.org/
 */
class RideDatabaseImpl(
    private val connectionFactory: PostgresConnectionFactory
) : RideDatabase {

    private val connection by lazy {
        connectionFactory.createConnection()
    }

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
                    "SELECT id, start_location, end_location, cost, status FROM rides WHERE id = ?"
                ).apply {
                    setString(1, id)
                }.executeQuery().use { resultSet ->
                    if (resultSet.next()) {
                        return RideDto(
                            id = resultSet.getString("id"),
                            startLocation = resultSet.getString("start_location"),
                            endLocation = resultSet.getString("end_location"),
                            cost = resultSet.getLong("cost"),
                            status = resultSet.getString("status")
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
