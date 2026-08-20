package com.travel.travelapp.data.remote.dto

import kotlinx.serialization.Serializable
import java.time.LocalDate

@Serializable
data class ExpenseResponse(
    val id: Long,
    val userId: Long,
    val name: String,
    val amount: Double,
    val tripId: Long,
    val date: String
)
