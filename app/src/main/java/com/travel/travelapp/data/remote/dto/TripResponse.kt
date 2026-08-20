package com.travel.travelapp.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class TripResponse(
    val id: Long,
    val title: String,
    val destination: String,
    val startDate: String, // LocalDate as "yyyy-MM-dd"
    val endDate: String,   // LocalDate as "yyyy-MM-dd"
    val userId: Long,
    val plannedBudget: Double = 0.0,
    val totalExpenses: Double = 0.0
)
