package com.travel.travelapp.screen.trips

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.travel.travelapp.domain.model.Document
import com.travel.travelapp.domain.model.ItineraryItem
import com.travel.travelapp.domain.model.PackingItem
import com.travel.travelapp.domain.model.Trip
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun TripDetailsScreen(
    viewModel: TripDetailsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var selectedTabIndex by remember { mutableIntStateOf(0) }

    TripDetailsContent(
        state = uiState,
        tabIndex = selectedTabIndex,
        onTabClick = { index ->
            selectedTabIndex = index
            viewModel.onTabSelected(index)
        },
        onTogglePackingItem = { item ->
            viewModel.togglePackingItem(item)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TripDetailsContent(
    state: TripDetailsUiState,
    tabIndex: Int,
    onTabClick: (Int) -> Unit,
    onTogglePackingItem: (PackingItem) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(state.trip?.title ?: "Trip Details") }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier.padding(padding).fillMaxSize()
        ) {
            when {
                state.isLoading && state.trip == null -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }
                state.error != null -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(text = state.error)
                    }
                }
                else -> {
                    TabRow(selectedTabIndex = tabIndex) {
                        val titles = listOf("Packing", "Itinerary", "Expenses", "Docs")
                        titles.forEachIndexed { index, title ->
                            Tab(
                                selected = tabIndex == index,
                                onClick = { onTabClick(index) },
                                text = { Text(title) }
                            )
                        }
                    }

                    when (tabIndex) {
                        0 -> PackingListContent(state.packingList, onTogglePackingItem)
                        1 -> ItineraryContent(state.itineraryList)
                        2 -> ExpenseContent(state.plannedBudget)
                        3 -> DocumentsContent(state.documentsList)
                    }
                }
            }
        }
    }
}

@Composable
fun PackingListContent(
    items: List<PackingItem>,
    onTogglePackingItem: (PackingItem) -> Unit
) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(items, key = { it.id }) { item ->
            Row(
                modifier = Modifier
                    .clickable{ onTogglePackingItem(item) }
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                    Text(
                        text = item.name,
                        modifier = Modifier.padding(start = 8.dp)
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    Checkbox(
                        checked = item.packed,
                        onCheckedChange = null
                    )
            }
        }
    }
}

@Composable
fun ItineraryContent(
    items: List<ItineraryItem>,
    onAddItem: () -> Unit = {}
) {
   val groupedItems = remember(items) {
        items.groupBy {
            LocalDate.parse(it.startDateTime.substring(0, 10))
        }.toSortedMap()
   }
    
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = onAddItem) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "Додати подію")
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
        ) {
            val formatter = DateTimeFormatter.ofPattern("d MMMM", Locale.getDefault())

            groupedItems.forEach { (date, itemsForDate) ->
                item {
                    Text(
                        text = date.format(formatter),
                        modifier = Modifier.padding(vertical = 12.dp),
                        style = MaterialTheme.typography.titleMedium
                    )
                }

                items(itemsForDate.sortedBy { it.exactTime }) { item ->
                    Column(modifier = Modifier.padding(vertical = 6.dp)) {
                        Text(
                            text = "${item.exactTime ?: "--:--"} - ${item.name}",
                            style = MaterialTheme.typography.bodyLarge
                        )
                        if (!item.description.isNullOrBlank()) {
                            Text(
                                text = item.description,
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.Gray
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ItineraryContentPreview() {

}

@Composable
fun ExpenseContent(expense: Double?) {
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Text("Expenses feature coming soon", style = MaterialTheme.typography.bodyLarge)
    }
}

@Composable
fun DocumentsContent(items: List<Document>) {
    Text(text = "Документів: ${items.size}", modifier = Modifier.padding(16.dp))
}

@Preview(showBackground = true)
@Composable
fun TripDetailsPreview() {
    val fakeState = TripDetailsUiState(
        trip = Trip(id = 1, title = "Відпустка у Парижі", destination = "Париж", startDate = LocalDate.parse("2025-02-02"), endDate = LocalDate.parse("2025-03-03")),
        isLoading = false
    )

    TripDetailsContent(
        state = fakeState,
        tabIndex = 0,
        onTabClick = {},
        onTogglePackingItem = {}
    )
}
