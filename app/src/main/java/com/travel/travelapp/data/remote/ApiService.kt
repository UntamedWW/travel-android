package com.travel.travelapp.data.remote

import com.travel.travelapp.data.remote.dto.*
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @GET("api/health")
    suspend fun getHealth(): Response<Unit>

    // --- Trips ---
    @GET("api/trips")
    suspend fun getTrips(): List<TripResponse>

    @GET("api/trips/{id}")
    suspend fun getTrip(@Path("id") id: Long): TripResponse

    @POST("api/trips")
    suspend fun createTrip(@Body request: TripRequest): TripResponse

    @PUT("api/trips/{id}")
    suspend fun updateTrip(@Path("id") id: Long, @Body request: TripRequest): TripResponse

    @DELETE("api/trips/{id}")
    suspend fun deleteTrip(@Path("id") id: Long): Response<Unit>

    // --- Itinerary ---
    @GET("api/itinerary-items/trip/{tripId}")
    suspend fun getItemsByTrip(@Path("tripId") tripId: Long): List<ItineraryItemResponse>

    @POST("api/itinerary-items")
    suspend fun createItem(@Body request: ItineraryItemRequest): ItineraryItemResponse

    @PUT("api/itinerary-items/{id}")
    suspend fun updateItem(@Path("id") id: Long, @Body request: ItineraryItemRequest): ItineraryItemResponse

    @DELETE("api/itinerary-items/{id}")
    suspend fun deleteItem(@Path("id") id: Long): Response<Unit>

    // --- Budget ---
    @GET("api/budgets/trip/{tripId}")
    suspend fun getBudgets(@Path("tripId") tripId: Long): List<BudgetResponse>

    @POST("api/budgets")
    suspend fun createBudget(@Body request: BudgetRequest): BudgetResponse

    @PUT("api/budgets/{id}")
    suspend fun updateBudget(@Path("id") id: Long, @Body request: BudgetRequest): BudgetResponse

    @DELETE("api/budgets/{id}")
    suspend fun deleteBudget(@Path("id") id: Long): Response<Unit>

    // --- Packing ---
    @GET("api/packing-items/trip/{tripId}")
    suspend fun getPackingItems(@Path("tripId") tripId: Long): List<PackingItemResponse>

    @POST("api/packing-items")
    suspend fun createPackingItem(@Body request: PackingItemRequest): PackingItemResponse

    @PUT("api/packing-items/{id}")
    suspend fun updatePackingItem(@Path("id") id: Long, @Body request: PackingItemRequest): PackingItemResponse

    @PATCH("api/packing-items/{id}/packed")
    suspend fun togglePacked(@Path("id") id: Long, @Body request: PackingItemPackedRequest): PackingItemResponse

    @DELETE("api/packing-items/{id}")
    suspend fun deletePackingItem(@Path("id") id: Long): Response<Unit>

    // --- Documents ---
    @GET("api/documents/trip/{tripId}")
    suspend fun getDocuments(@Path("tripId") tripId: Long): List<DocumentResponse>

    @POST("api/documents")
    suspend fun createDocument(@Body request: DocumentRequest): DocumentResponse

    @Multipart
    @POST("api/documents/upload")
    suspend fun uploadDocument(
        @Part file: MultipartBody.Part
    ): Response<Unit>

    @Streaming
    @GET("api/documents/{id}/file")
    suspend fun downloadDocument(@Path("id") id: Long): Response<ResponseBody>

    @PUT("api/documents/{id}")
    suspend fun updateDocument(@Path("id") id: Long, @Body request: DocumentRequest): DocumentResponse

    @DELETE("api/documents/{id}")
    suspend fun deleteDocument(@Path("id") id: Long): Response<Unit>
}
