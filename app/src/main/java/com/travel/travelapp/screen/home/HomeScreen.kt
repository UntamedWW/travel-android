package com.travel.travelapp.screen.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.travel.travelapp.domain.model.Trip
import com.travel.travelapp.screen.trips.TripItem
import com.travel.travelapp.ui.theme.TravelAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onLogout: () -> Unit,
    onNavigateToTrips: () -> Unit,
    onNavigateToTripDetails: (Long) -> Unit,
    onAddTrip: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

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

                uiState.nearestTrip != null -> {
                    Column(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .fillMaxSize()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("Your next adventure:", style = MaterialTheme.typography.headlineSmall)

                        Spacer(modifier = Modifier.height(16.dp))

                        TripItem(trip = uiState.nearestTrip, onClick = { onNavigateToTripDetails(uiState.nearestTrip.id) })

                        Spacer(modifier = Modifier.height(20.dp))

                        Button(onClick = onNavigateToTrips) {
                            Text("View my trips")
                        }
                    }
                }

                else -> {
                    Column(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .fillMaxSize()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                    ) {
                        Text("No trips planned yet")
                        Button(onClick = onAddTrip) {
                            Text("Add first trip")
                        }
                    }
                }
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    TravelAppTheme {
        HomeScreenContent(
            uiState = HomeUiState(
                isLoading = false,
                nearestTrip = Trip(
                    id = 1,
                    title = "Birthday in Paris",
                    destination = "Paris, France",
                    startDate = "2023-07-19",
                    endDate = "2023-07-20",
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