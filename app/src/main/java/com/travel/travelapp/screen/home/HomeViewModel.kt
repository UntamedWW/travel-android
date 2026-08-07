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
import javax.inject.Inject

data class HomeUiState(
    val isLoading: Boolean = false,
    val nearestTrip: Trip? = null,
    val error: String? = null
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
        // TODO: Implement loadNearestTrip()
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            val result = tripRepository.getTrips()
                .onSuccess { trips ->
                    val nearest = trips.minByOrNull { it.startDate }

                    _uiState.update { it.copy(
                        isLoading = false,
                        nearestTrip = nearest
                    ) }
                }
                .onFailure { exception ->
                    _uiState.update {it.copy(
                        isLoading = false,
                        error = exception.message ?: "Unknown error"
                    )}
                }
        }
    }

}