package com.travel.travelapp.data.mapper

import com.travel.travelapp.data.remote.dto.TripResponse
import com.travel.travelapp.domain.model.Trip

fun TripResponse.toDomain(): Trip {
    return Trip(
        id = id,
        title = title,
        destination = destination,
        startDate = startDate,
        endDate = endDate
    )
}
