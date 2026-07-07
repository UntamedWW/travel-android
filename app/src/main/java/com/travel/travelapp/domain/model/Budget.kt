package com.travel.travelapp.domain.model

data class Budget(
    val id: Long,
    val type: BudgetType,
    val category: String,
    val amount: Double
)

enum class BudgetType {
    EXPENSE, INCOME
}
