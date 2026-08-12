package com.travel.travelapp.domain.repository

import com.travel.travelapp.data.remote.dto.TripRequest
import com.travel.travelapp.domain.model.Budget
import com.travel.travelapp.domain.model.Document
import com.travel.travelapp.domain.model.ItineraryItem
import com.travel.travelapp.domain.model.PackingItem
import com.travel.travelapp.domain.model.Trip

interface TravelRepository {
    suspend fun getTrips(): Result<List<Trip>>
    suspend fun getTrip(id: Long): Result<Trip>
    suspend fun getPackingList(tripId: Long) : Result<List<PackingItem>>
    suspend fun getItineraryList(tripId: Long) : Result<List<ItineraryItem>>
    suspend fun getDocumentsList(tripId: Long) : Result<List<Document>>
    suspend fun getBudget(tripId: Long) : Result<Budget>
    suspend fun createTrip(trip: TripRequest): Result<Trip>
    suspend fun updateTrip(id: Long, trip: TripRequest): Result<Trip>
    suspend fun deleteTrip(id: Long): Result<Unit>
}
