package com.travel.travelapp.data.repository

import com.travel.travelapp.data.local.DataStoreManager
import com.travel.travelapp.data.remote.ApiService
import com.travel.travelapp.domain.repository.TravelRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TravelRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val dataStoreManager: DataStoreManager
) : TravelRepository {

    override suspend fun fetchData(): String {
        return apiService.getExampleData()
    }

    //.authToken - example Flow
    override val exampleData: Flow<String?> = dataStoreManager.authToken

    //.saveAuthToken(value) - save example string(value)
    override suspend fun saveToDataStore(value: String) {
        dataStoreManager.saveAuthToken(value)
    }
}
