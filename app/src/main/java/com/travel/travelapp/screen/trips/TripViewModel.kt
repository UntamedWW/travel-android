package com.travel.travelapp.screen.trips

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.travel.travelapp.domain.model.Trip
import com.travel.travelapp.domain.repository.TravelRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class TripUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val trips: List<Trip> = emptyList()
)

@HiltViewModel
class TripViewModel @Inject constructor(
    private val tripRepository: TravelRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(TripUiState())
    val uiState: StateFlow<TripUiState> = _uiState.asStateFlow()

    init {
        loadTrips()
    }

    fun loadTrips() {
        viewModelScope.launch {
            // 1. Починаємо завантаження
            _uiState.update { it.copy(isLoading = true, error = null) }
            
            val result = tripRepository.getTrips()
            
            result.onSuccess { fetchedTrips ->
                // 2. Зберігаємо отриманий список у стан
                _uiState.update { it.copy(
                    isLoading = false, 
                    trips = fetchedTrips 
                ) }
            }.onFailure { e ->
                // 3. Якщо помилка — записуємо її і очищаємо список (за бажанням)
                _uiState.update { it.copy(
                    isLoading = false, 
                    error = e.message ?: "Unknown error",
                    trips = emptyList()
                ) }
            }
        }
    }
}
