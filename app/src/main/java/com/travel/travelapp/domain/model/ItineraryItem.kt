package com.travel.travelapp.domain.model

data class ItineraryItem(
    val id: Long,
    val name: String,
    val description: String?,
    val location: String?,
    val startDateTime: String,
    val endDateTime: String?
)
