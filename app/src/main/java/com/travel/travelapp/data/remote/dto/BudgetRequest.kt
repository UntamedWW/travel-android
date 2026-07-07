package com.travel.travelapp.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class BudgetRequest(
    val type: String, // String representation of BudgetType enum
    val category: String?,
    val amount: Double,
    val tripId: Long
)
