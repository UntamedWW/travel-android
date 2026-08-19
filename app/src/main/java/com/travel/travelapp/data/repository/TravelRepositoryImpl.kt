package com.travel.travelapp.data.repository

import com.travel.travelapp.data.local.DataStoreManager
import com.travel.travelapp.data.mapper.toDomain
import com.travel.travelapp.data.remote.ApiService
import com.travel.travelapp.data.remote.dto.TripRequest
import com.travel.travelapp.domain.model.Document
import com.travel.travelapp.domain.model.Expense
import com.travel.travelapp.domain.model.ItineraryItem
import com.travel.travelapp.domain.model.PackingItem
import com.travel.travelapp.domain.model.Trip
import com.travel.travelapp.domain.repository.TravelRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TravelRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val dataStoreManager: DataStoreManager
) : TravelRepository {

    override suspend fun getTrips(): Result<List<Trip>> {
        return try {
            val response = apiService.getTrips()
            Result.success(response.map { it.toDomain() })
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getTrip(id: Long): Result<Trip> {
        return try {
            val response = apiService.getTrip(id)
            Result.success(response.toDomain())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getPackingList(tripId: Long): Result<List<PackingItem>> {
        TODO("Not yet implemented")
    }

    override suspend fun getItineraryList(tripId: Long): Result<List<ItineraryItem>> {
        TODO("Not yet implemented")
    }

    override suspend fun getDocumentsList(tripId: Long): Result<List<Document>> {
        TODO("Not yet implemented")
    }

    override suspend fun getPlannedBudget(tripId: Long): Result<Double> {
        TODO("Not yet implemented")
    }

    override suspend fun getExpensesList(tripId: Long): Result<List<Expense>> {
        TODO("Not yet implemented")
    }

    override suspend fun addExpense(tripId: Long, expense: Expense): Result<Unit> {
        TODO("Not yet implemented")
    }



    // TODO: Implement createTrip(trip: TripRequest)
    override suspend fun createTrip(trip: TripRequest): Result<Trip> {
        return try {
            val response = apiService.createTrip(trip)
            Result.success(response.toDomain())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    // TODO: Implement updateTrip(id: Long, trip: TripRequest)
    override suspend fun updateTrip(id: Long, trip: TripRequest): Result<Trip> {
        return try {
            val response = apiService.updateTrip(id, trip)
            Result.success(response.toDomain())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    // TODO: Implement deleteTrip(id: Long)
    override suspend fun deleteTrip(id: Long): Result<Unit> {
        return try {
            val response = apiService.deleteTrip(id)
            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                Result.failure(Exception("Failed to delete trip"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updatePackingItem(item: PackingItem): Result<Unit> {
        TODO("Not yet implemented")
    }
}
