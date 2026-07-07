package com.travel.travelapp.domain.model

data class Trip(
    val id: Long,
    val title: String,
    val destination: String,
    val startDate: String,
    val endDate: String,
    val totalTasks: Int = 0,
    val completedTasks: Int = 0,
    val expectedBudget: Double = 0.0,
    val spentBudget: Double = 0.0
) {
    val preparationProgress: Float
        get() = if (totalTasks > 0) completedTasks.toFloat() / totalTasks else 0f
}
