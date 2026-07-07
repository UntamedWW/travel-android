package com.travel.travelapp.data.mapper

import com.travel.travelapp.data.remote.dto.UserResponse
import com.travel.travelapp.domain.model.User

fun UserResponse.toDomain(): User {
    return User(
        id = id,
        email = email
    )
}
