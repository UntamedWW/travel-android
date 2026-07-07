package com.travel.travelapp.data.mapper

import com.travel.travelapp.data.remote.dto.DocumentResponse
import com.travel.travelapp.domain.model.Document

fun DocumentResponse.toDomain(): Document {
    return Document(
        id = id,
        name = name,
        type = type,
        number = number,
        url = url,
        expirationDate = expirationDate
    )
}
