package com.travel.travelapp.screen.trips

import android.os.BugreportManager
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.travel.travelapp.domain.model.Document
import com.travel.travelapp.domain.model.Expense
import com.travel.travelapp.domain.model.ItineraryItem
import com.travel.travelapp.domain.model.PackingItem
import com.travel.travelapp.domain.model.Trip
import com.travel.travelapp.domain.repository.TravelRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class TripDetailsUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val trip: Trip? = null,
    val packingList: List<PackingItem> = emptyList(),
    val itineraryList: List<ItineraryItem> = emptyList(),
    val plannedBudget: Double = 0.0,
    val expensesList: List<Expense> = emptyList(),
    val expensesTotal: Double = 0.0,
    val documentsList: List<Document> = emptyList(),
)

@HiltViewModel
class TripDetailsViewModel @Inject constructor(
    private val tripRepository: TravelRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val id: Long = checkNotNull(savedStateHandle["tripId"])
    private val _uiState = MutableStateFlow(TripDetailsUiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadTripGeneralInfo()
    }


    fun loadTripGeneralInfo() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            val result = tripRepository.getTrip(id)

            result.onSuccess { trip ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        trip = trip
                    )
                }
            }
                .onFailure { exception ->
                    _uiState.update { currentState ->
                        currentState.copy(
                            error = exception.message ?: "Unknown error",
                            isLoading = false
                        )
                    }
                }
        }
    }

    fun onTabSelected(index: Int){
        when (index) {
            0 -> loadPackingList()
            1 -> loadItinerary()
            2 -> loadBudget()
            3 -> loadDocuments()
        }
    }

    fun loadPackingList() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            val result = tripRepository.getPackingList(id)

            result.onSuccess { list ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        packingList = list
                    )
                }
            }
                .onFailure { exception ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = exception.message ?: "Unknown error",
                            packingList = emptyList()
                        )
                    }
                }
        }
    }

    fun loadItinerary() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            val result = tripRepository.getItineraryList(id)

            result.onSuccess { list ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        itineraryList = list
                    )
                }
            }

                .onFailure { exception ->
                    _uiState.update {
                        it.copy(
                            error = exception.message ?: "Unknown error",
                            isLoading = false,
                            itineraryList = emptyList()
                        )
                    }
                }
        }
    }

    fun loadBudget() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            val budgetResult = tripRepository.getPlannedBudget(id)
            val expensesResult = tripRepository.getExpensesList(id)

            budgetResult.onSuccess { amount ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        plannedBudget = amount
                    )
                }
            }

                .onFailure { exception ->
                    _uiState.update {
                        it.copy(
                            error = exception.message ?: "Unknown error",
                            isLoading = false,
                            plannedBudget = 0.0
                        )
                    }
                }

            expensesResult.onSuccess { expenses ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        expensesTotal = expenses.sumOf { it.amount },
                        expensesList = expenses
                    )
                }
            }

            .onFailure { exception ->
                _uiState.update {
                    it.copy(
                        error = exception.message ?: "Unknown error",
                        isLoading = false,
                        expensesTotal = 0.0,
                        expensesList = emptyList()
                    )
                }
            }
        }
    }

    fun addExpense(name: String, amount: Double){
        viewModelScope.launch {
            val newExpense = Expense(
                id = 0L,
                tripId = id,
                name = name,
                amount = amount,
                date = java.time.LocalDate.now(),
                )
            val result = tripRepository.addExpense(tripId = id, newExpense)

            if(result.isSuccess) {
                loadBudget()
            }
        }
    }

    fun loadDocuments() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            val result = tripRepository.getDocumentsList(id)

            result.onSuccess { list ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        documentsList = list
                    )
                }
            }
            .onFailure { exception ->
                _uiState.update {
                    it.copy(
                        error = exception.message ?: "Unknown error",
                        isLoading = false
                    )
                }
            }
        }
    }

    fun togglePackingItem(item: PackingItem) {
        viewModelScope.launch {
            val updatedItem = item.copy(packed = !item.packed)
            val result = tripRepository.updatePackingItem(updatedItem)

            if(result.isSuccess) {
                loadPackingList()
            }
        }
    }
}

