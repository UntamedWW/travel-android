package com.travel.travelapp.domain.repository

import kotlinx.coroutines.flow.Flow

interface TravelRepository {
    suspend fun fetchData(): String
    val exampleData: Flow<String?>
    suspend fun saveToDataStore(value: String)
}