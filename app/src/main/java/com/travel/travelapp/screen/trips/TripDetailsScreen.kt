package com.travel.travelapp.screen.trips

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.travel.travelapp.domain.model.Document
import com.travel.travelapp.domain.model.ItineraryItem
import com.travel.travelapp.domain.model.PackingItem
import com.travel.travelapp.domain.model.Trip
import java.time.LocalDate

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
                        0 -> PackingListContent(items = state.packingList, onTogglePackingItem = onTogglePackingItem)
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
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                    Text(
                        text = item.name,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                    Checkbox(
                        checked = item.packed,
                        onCheckedChange = { onTogglePackingItem(item) }
                    )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PackingListContentPreview() {
    val fakeState = TripDetailsUiState(
        // Fixed: Changed date format from "dd/MM/yyyy" to ISO-8601 "yyyy-MM-dd" for LocalDate.parse
        trip = Trip(id = 1, title = "Відпустка у Парижі", destination = "Париж", startDate = LocalDate.parse("2025-02-02"), endDate = LocalDate.parse("2025-03-03")),
        isLoading = false,
        packingList = listOf(
            PackingItem(id = 1, name = "Палатка", packed = false),
            PackingItem(id = 2, name = "Кросівки", packed = true)
        )
    )
    PackingListContent(items = fakeState.packingList, onTogglePackingItem = {})
}

@Composable
fun ItineraryContent(state: List<ItineraryItem>) {
    // TODO: Напиши список справ, використовуючи state.itineraryList
}

@Composable
fun ExpenseContent(expense: Double?) {
    // TODO:
    //  Summary card: "Planned", "Spent", "Remaining"
    //  History List: sorted by date transactions
    //  Add/Edit buttons (+delete near each transaction)

    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Row() {
                Column() {
                    Text("Planned")
                    Text("")
                }
                Column() {
                    Text("Spend")
                    Text("")
                }
                Column() {
                    Text("Planned")
                    Text("$")
                }
            }
        }
      }

 }

@Composable
fun DocumentsContent(items: List<Document>) {
    // TODO: Напиши список файлів, використовуючи state.documentsList
    Text(text = "Документів: ${items.size}", modifier = Modifier.padding(16.dp))
}

@Preview(showBackground = true)
@Composable
fun TripDetailsPreview() {
    val fakeState = TripDetailsUiState(
        // Fixed: Changed date format from "dd/MM/yyyy" to ISO-8601 "yyyy-MM-dd" for LocalDate.parse
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
