package com.travel.travelapp.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class UserRequest(
    val email: String,
    val password: String
)
