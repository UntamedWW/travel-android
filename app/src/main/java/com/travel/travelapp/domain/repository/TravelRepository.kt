package com.travel.travelapp.domain.repository

import com.travel.travelapp.data.remote.dto.TripRequest
import com.travel.travelapp.domain.model.Trip

interface TravelRepository {
    // TODO: Implement getTrips() to fetch all user trips
    suspend fun getTrips(): Result<List<Trip>>
    // TODO: Implement getTrip(id: Long) to fetch a specific trip detail
    suspend fun getTrip(id: Long): Result<Trip>
    // TODO: Implement createTrip(trip: TripRequest)
    suspend fun createTrip(trip: TripRequest): Result<Trip>
    // TODO: Implement updateTrip(id: Long, trip: TripRequest)
    suspend fun updateTrip(id: Long, trip: TripRequest): Result<Trip>
    // TODO: Implement deleteTrip(id: Long)
    suspend fun deleteTrip(id: Long): Result<Unit>
}
