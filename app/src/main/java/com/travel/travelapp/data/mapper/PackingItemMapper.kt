package com.travel.travelapp.data.mapper

import com.travel.travelapp.data.remote.dto.PackingItemResponse
import com.travel.travelapp.domain.model.PackingItem

fun PackingItemResponse.toDomain() : PackingItem {
    return PackingItem(
        id = id,
        name = name,
        packed = packed
    )
}
