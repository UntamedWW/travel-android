package com.travel.travelapp.data.repository

import com.travel.travelapp.data.remote.ApiService
import com.travel.travelapp.domain.repository.ItineraryRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ItineraryRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : ItineraryRepository {
    // TODO: Implement getItemsByTrip(tripId: Long) using apiService.getItemsByTrip(tripId)
    // TODO: Implement createItem(item: ItineraryItemRequest)
    // TODO: Implement updateItem(id: Long, item: ItineraryItemRequest)
    // TODO: Implement deleteItem(id: Long)
}
