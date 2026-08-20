package com.travel.travelapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.travel.travelapp.screen.auth.AuthViewModel
import com.travel.travelapp.screen.auth.LoginScreen
import com.travel.travelapp.screen.auth.RegisterScreen
import com.travel.travelapp.screen.home.HomeScreen
import com.travel.travelapp.screen.trips.TripDetailsScreen
import com.travel.travelapp.screen.trips.TripsScreen
import com.travel.travelapp.ui.theme.TravelAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TravelAppTheme {
                val navController = rememberNavController()
                val authViewModel: AuthViewModel = hiltViewModel()
                val uiState by authViewModel.uiState.collectAsStateWithLifecycle()

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        if(uiState.isLoggedIn) {
                            BottomNavigationBar(navController = navController)
                        } }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = if (uiState.isLoggedIn) "home" else "login",
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable("login") {
                            LoginScreen(
                                onNavigateToRegister = { navController.navigate("register") },
                                onLoginSuccess = { 
                                    navController.navigate("home") {
                                        popUpTo("login") { inclusive = true }
                                    }
                                }
                            )
                        }
                        composable("register") {
                            RegisterScreen(
                                onNavigateToLogin = { navController.navigate("login") },
                                onRegisterSuccess = { 
                                    navController.navigate("home") {
                                        popUpTo("register") { inclusive = true }
                                    }
                                }
                            )
                        }
                        composable("home") {
                            HomeScreen(
                                viewModel = hiltViewModel(),
                                onLogout = {
                                    authViewModel.logout()
                                    navController.navigate("login") {
                                        popUpTo("home") { inclusive = true }
                                    }
                                },
                                onNavigateToTrips = { navController.navigate("trips")},
                                onNavigateToTripDetails = { tripId ->
                                    navController.navigate("trips/$tripId")
                                },
                                onAddTrip = {
                                    navController.navigate("add_trip")
                                }
                            )
                        }

                        composable("trips"){
                            TripsScreen( onNavigateToTripDetails = { tripId ->
                                navController.navigate("trips/$tripId")
                            },
                                onAddTrip = {
                                    navController.navigate("add_trip")
                                }
                            )
                        }

                        composable("trips/{tripId}") {
                            TripDetailsScreen(
                                viewModel = hiltViewModel()
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: androidx.navigation.NavHostController) {
    val items = listOf("home", "trips", "profile")
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar {
        items.forEach { route ->
            NavigationBarItem(
                icon = {
                    val icon = when(route) {
                        "home" -> Icons.Default.Home
                        "trips" -> Icons.Default.List
                        else -> Icons.Default.Person
                    }
                    Icon(icon, contentDescription = route)
                },
                label = { Text(route.replaceFirstChar { it.uppercase() }) },
                selected = currentRoute == route,
                onClick = {
                    navController.navigate(route) {
                        popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BottomNavigationBarPreview() {
    TravelAppTheme {
        BottomNavigationBar(navController = rememberNavController())
    }
}


@Composable
fun HomeScreenPlaceholder(
    onLogout: () -> Unit,
    onNavigateToTrips: () -> Unit,
    onNavigateToTripDetails: (Long) -> Unit,
    onAddTrip: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = androidx.compose.foundation.layout.Arrangement.Center,
        horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
    ) {
        HomeScreen (
            onLogout = onLogout,
            onNavigateToTrips = onNavigateToTrips,
            onNavigateToTripDetails = onNavigateToTripDetails,
            onAddTrip = onAddTrip
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    TravelAppTheme {
        HomeScreenPlaceholder(
            onLogout = {},
            onNavigateToTrips = {},
            onNavigateToTripDetails = {},
            onAddTrip = {}
        )
    }
}