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
    val nearestTrips: List<Trip> = emptyList(),
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
                    val upcoming = trips
                        .filter { !it.endDate.isBefore(today) }
                        .sortedBy { it.startDate }
                        .take(3)
                    
                    // Рахуємо дні для найближчої поїздки
                    val days = upcoming.firstOrNull()?.let {
                        daysUntilTrip(it.startDate)
                    }

                    _uiState.update { it.copy(
                        isLoading = false,
                        nearestTrips = upcoming,
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
