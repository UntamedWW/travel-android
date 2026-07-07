package com.travel.travelapp.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class PackingItemPackedRequest(
    val packed: Boolean
)
