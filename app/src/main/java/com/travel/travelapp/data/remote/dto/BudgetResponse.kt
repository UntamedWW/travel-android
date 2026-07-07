package com.travel.travelapp.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class BudgetResponse(
    val id: Long,
    val userId: Long,
    val type: String,
    val category: String,
    val amount: Double,
    val tripId: Long
)
