package com.travel.travelapp.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class AuthResponse(
    val id: Long,
    val email: String,
    val token: String
)
