package com.travel.travelapp.data.remote

import kotlinx.serialization.Serializable
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("/api/auth/login")
    suspend fun login(@Body request: AuthRequest): AuthResponse

    @POST("/api/auth/register")
    suspend fun register(@Body request: AuthRequest): AuthResponse

    // TODO: Add other auth related endpoints here
}

@Serializable
data class AuthRequest(
    val email: String,
    val password: String
)

@Serializable
data class AuthResponse(
    val id: Long,
    val email: String,
    val token: String
)
