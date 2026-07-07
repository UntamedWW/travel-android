package com.travel.travelapp.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class ItineraryItemResponse(
    val id: Long,
    val name: String,
    val description: String?,
    val location: String?,
    val startDateTime: String, // ISO-8601 string from LocalDateTime
    val endDateTime: String?,
    val tripId: Long
)
