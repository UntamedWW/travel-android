package com.travel.travelapp.data.repository

import com.travel.travelapp.data.remote.ApiService
import com.travel.travelapp.domain.repository.DocumentRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DocumentRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : DocumentRepository {
    // TODO: Implement getDocuments(tripId: Long) using apiService.getDocuments(tripId)
    // TODO: Implement createDocument(name: String, type: String, tripId: Long)
    // TODO: Implement uploadDocument(id: Long, filePath: String)
    // TODO: Implement downloadDocument(id: Long)
    // TODO: Implement updateDocument(id: Long, name: String, type: String)
    // TODO: Implement deleteDocument(id: Long)
}
