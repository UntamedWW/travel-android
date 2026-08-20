package com.travel.travelapp.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class ExpenseRequest(
    val name: String,
    val amount: Double,
    val tripId: Long,
    val date: String // LocalDate as "yyyy-MM-dd"
)
