package com.travel.travelapp.screen.trips

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TripDetailsScreen(
    viewModel: TripDetailsViewModel = hiltViewModel()

) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    //val selectedTabIndex = remember { mutableStateOf(0) }
    //Scaffold
    //{ column -> TabRow () -> when(selectedTabIndex.value) {}
    //when (1) - loadPackingList()



}