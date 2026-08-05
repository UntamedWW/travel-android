package com.travel.travelapp.data.repository

import com.travel.travelapp.data.remote.ApiService
import com.travel.travelapp.domain.repository.PackingRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PackingRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : PackingRepository {
    // TODO: Implement getPackingItems(tripId: Long) using apiService.getPackingItems(tripId)
    // TODO: Implement createPackingItem(name: String, tripId: Long) using apiService.createPackingItem(...)
    // TODO: Implement updatePackingItem(id: Long, name: String)
    // TODO: Implement togglePacked(id: Long, packed: Boolean) using apiService.togglePacked(...)
    // TODO: Implement deletePackingItem(id: Long)
}
