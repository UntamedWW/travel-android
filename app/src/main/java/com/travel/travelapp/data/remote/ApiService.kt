package com.travel.travelapp.data.remote

import retrofit2.http.GET

interface ApiService {
    @GET("example")
    suspend fun getExampleData(): String // Replace with your data model
}