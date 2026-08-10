package com.travel.travelapp.domain.model

import java.time.LocalDate

data class Trip(
    val id: Long,
    val title: String,
    val destination: String,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val totalTasks: Int = 0,
    val completedTasks: Int = 0,
    val expectedBudget: Double = 0.0,
    val spentBudget: Double = 0.0
) {
    val preparationProgress: Float
        get() = if (totalTasks > 0) completedTasks.toFloat() / totalTasks else 0f
}
