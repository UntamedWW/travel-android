package com.travel.travelapp.data.repository

import com.travel.travelapp.data.remote.ApiService
import com.travel.travelapp.domain.repository.ExpenseRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ExpenseRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : ExpenseRepository {
    // TODO: Implement getExpenseByTripId(tripId: Long) mapping to domain models
    // TODO: Implement createExpense(expense: ExpenseRequest) using apiService
    // TODO: Implement updateExpense(id: Long, expense: ExpenseRequest)
    // TODO: Implement deleteExpense(id: Long)
}
