package com.travel.travelapp.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.travel.travelapp.domain.model.Trip
import com.travel.travelapp.domain.repository.TravelRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import javax.inject.Inject

data class HomeUiState(
    val isLoading: Boolean = false,
    val nearestTrip: Trip? = null,
    val error: String? = null,
    val daysUntilTrip: Int? = null
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val tripRepository: TravelRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadNearestTrip()
    }

    private fun loadNearestTrip() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            tripRepository.getTrips()
                .onSuccess { trips ->
                    val today = LocalDate.now()
                    val nearest = trips
                        .filter { trip ->
                            // Залишаємо подорож, якщо вона ще не закінчилася
                            !trip.endDate.isBefore(today)
                        }
                        .minByOrNull { it.startDate }
                    
                    val days = nearest?.let {
                        daysUntilTrip(it.startDate)
                    }

                    _uiState.update { it.copy(
                        isLoading = false,
                        nearestTrip = nearest,
                        daysUntilTrip = days
                    ) }
                }
                .onFailure { exception ->
                    _uiState.update { it.copy(
                        isLoading = false,
                        error = exception.message ?: "Unknown error"
                    ) }
                }
        }
    }

    private fun daysUntilTrip(startDate: LocalDate): Int {
        val today = LocalDate.now()
        return ChronoUnit.DAYS.between(today, startDate).toInt()
    }
}
