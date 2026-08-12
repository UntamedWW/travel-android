package com.travel.travelapp.screen.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.travel.travelapp.domain.model.Trip
import com.travel.travelapp.screen.trips.TripItem
import com.travel.travelapp.ui.theme.TravelAppTheme
import java.time.LocalDate
import java.time.temporal.ChronoUnit

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onLogout: () -> Unit,
    onNavigateToTrips: () -> Unit,
    onNavigateToTripDetails: (Long) -> Unit,
    onAddTrip: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    HomeScreenContent(
        uiState = uiState,
        onLogout = onLogout,
        onNavigateToTrips = onNavigateToTrips,
        onNavigateToTripDetails = onNavigateToTripDetails,
        onAddTrip = onAddTrip
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenContent(
    uiState: HomeUiState,
    onLogout: () -> Unit,
    onNavigateToTrips: () -> Unit,
    onNavigateToTripDetails: (Long) -> Unit,
    onAddTrip: () -> Unit
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(
                    "Home",
                    style = MaterialTheme.typography.titleLarge
                ) },
                actions = {
                    IconButton(onClick = onLogout) {
                        Icon(Icons.Default.ExitToApp, contentDescription = "Logout")
                    }
                }
            )
        },
    ) { padding ->
        Box(modifier = Modifier.padding(padding).fillMaxSize()) {
            when {
                uiState.isLoading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }

                uiState.error != null -> {
                    Text(text = uiState.error, color = Color.Red, modifier = Modifier.align(Alignment.Center))
                }

                uiState.nearestTrips.isNotEmpty() -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Top
                    ) {
                        Text(
                            text = "Your upcoming adventures",
                            style = MaterialTheme.typography.headlineSmall,
                            modifier = Modifier.padding(top = 8.dp, bottom = 12.dp)
                        )

                        uiState.nearestTrips.forEach { trip ->
                            val today = LocalDate.now()
                            val diff = ChronoUnit.DAYS.between(today, trip.startDate).toInt()
                            
                            val status = when {
                                diff == 0 -> "Today!"
                                diff < 0 -> "Ongoing"
                                else -> "$diff days left"
                            }

                            TripItem(
                                trip = trip,
                                statusText = status,
                                onClick = { onNavigateToTripDetails(trip.id) },
                                onDocumentsClick = { /*TODO*/ },
                                onPackingListClick = { /*TODO*/ }
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Button(onClick = onNavigateToTrips) {
                            Text("View all my trips")
                        }
                    }
                }

                else -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                    ) {
                        Icon(
                            imageVector = Icons.Default.DateRange,
                            contentDescription = null,
                            modifier = Modifier.size(64.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            "No trips planned yet",
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(
                            "The world is waiting for you! Time to plan your first adventure.",
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(horizontal = 32.dp, vertical = 8.dp)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(onClick = onAddTrip) {
                            Text("Plan a trip")
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, name = "With Trips")
@Composable
fun HomeScreenPreview() {
    TravelAppTheme {
        HomeScreenContent(
            uiState = HomeUiState(
                isLoading = false,
                nearestTrips = listOf(
                    Trip(
                        id = 2,
                        title = "Business Trip to Kyiv",
                        destination = "Kyiv, Ukraine",
                        startDate = LocalDate.now(),
                        endDate = LocalDate.now().plusDays(3),
                    ),
                    Trip(
                        id = 1,
                        title = "Birthday in Paris",
                        destination = "Paris, France",
                        startDate = LocalDate.now().plusDays(5),
                        endDate = LocalDate.now().plusDays(10),
                    )
                ),
                error = null
            ),
            onLogout = {},
            onNavigateToTrips = {},
            onNavigateToTripDetails = {},
            onAddTrip = {}
        )
    }
}

@Preview(showBackground = true, name = "Empty State")
@Composable
fun HomeScreenEmptyPreview() {
    TravelAppTheme {
        HomeScreenContent(
            uiState = HomeUiState(
                isLoading = false,
                nearestTrips = emptyList(),
                error = null
            ),
            onLogout = {},
            onNavigateToTrips = {},
            onNavigateToTripDetails = {},
            onAddTrip = {}
        )
    }
}
