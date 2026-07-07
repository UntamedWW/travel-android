package com.travel.travelapp.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class PackingItemResponse(
    val id: Long,
    val name: String,
    val packed: Boolean,
    val tripId: Long
)
