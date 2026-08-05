package com.travel.travelapp.data.repository

import com.travel.travelapp.data.remote.ApiService
import com.travel.travelapp.domain.repository.BudgetRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BudgetRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : BudgetRepository {
    // TODO: Implement getBudgets(tripId: Long) using apiService.getBudgets(tripId) and mapping to domain models
    // TODO: Implement createBudget(budget: BudgetRequest) using apiService.createBudget(budget)
    // TODO: Implement updateBudget(id: Long, budget: BudgetRequest)
    // TODO: Implement deleteBudget(id: Long)
}
