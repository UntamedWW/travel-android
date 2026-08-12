package com.travel.travelapp.screen.trips

import android.os.BugreportManager
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.travel.travelapp.domain.model.Budget
import com.travel.travelapp.domain.model.Document
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
    val budget: Budget? = null,
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

            val result = tripRepository.getBudget(id)

            result.onSuccess { amount ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        budget = amount
                    )
                }
            }

                .onFailure { exception ->
                    _uiState.update {
                        it.copy(
                            error = exception.message ?: "Unknown error",
                            isLoading = false,
                            budget = null
                        )
                    }
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
}

