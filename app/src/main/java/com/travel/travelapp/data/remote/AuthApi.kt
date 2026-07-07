package com.travel.travelapp.data.remote

import com.travel.travelapp.data.remote.dto.AuthRequest
import com.travel.travelapp.data.remote.dto.AuthResponse
import com.travel.travelapp.data.remote.dto.UserRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("api/auth/login")
    suspend fun login(@Body request: AuthRequest): AuthResponse

    @POST("api/auth/register")
    suspend fun register(@Body request: UserRequest): AuthResponse
}
