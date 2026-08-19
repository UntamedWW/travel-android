package com.travel.travelapp.domain.model

import java.time.LocalDate

data class Expense(
    val id: Long,
    val tripId: Long,
    val name: String,
    val amount: Double,
    val date: LocalDate
)
