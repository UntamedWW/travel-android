package com.travel.travelapp.data.mapper

import com.travel.travelapp.data.remote.dto.ExpenseResponse
import com.travel.travelapp.domain.model.Expense
import java.time.LocalDate

fun ExpenseResponse.toDomain(): Expense {
    return Expense(
        id = id,
        name = name ?: "",
        amount = amount,
        date = LocalDate.parse(date),
        tripId = tripId
    )
}
