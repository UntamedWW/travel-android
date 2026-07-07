package com.travel.travelapp.data.mapper

import com.travel.travelapp.data.remote.dto.ItineraryItemResponse
import com.travel.travelapp.domain.model.ItineraryItem

fun ItineraryItemResponse.toDomain(): ItineraryItem {
    return ItineraryItem(
        id = id,
        name = name,
        description = description,
        location = location,
        startDateTime = startDateTime,
        endDateTime = endDateTime
    )
}
