package com.travel.travelapp.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class TripRequest(
    val title: String,
    val destination: String,
    val startDate: String, // LocalDate as "yyyy-MM-dd"
    val endDate: String    // LocalDate as "yyyy-MM-dd"
)
