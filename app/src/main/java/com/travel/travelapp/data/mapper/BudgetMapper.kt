package com.travel.travelapp.data.mapper

import com.travel.travelapp.data.remote.dto.BudgetResponse
import com.travel.travelapp.domain.model.Budget
import com.travel.travelapp.domain.model.BudgetType

fun BudgetResponse.toDomain(): Budget {
    return Budget(
        id = id,
        type = try {
            BudgetType.valueOf(type)
        } catch (e: Exception) {
            BudgetType.EXPENSE // Default or handle error appropriately
        },
        category = category ?: "",
        amount = amount
    )
}
