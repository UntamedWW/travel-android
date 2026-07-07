package com.travel.travelapp.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class PackingItemRequest(
    val name: String,
    val tripId: Long
)
