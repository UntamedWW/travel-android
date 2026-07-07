package com.travel.travelapp.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class DocumentResponse(
    val id: Long,
    val name: String,
    val type: String?,
    val number: String?,
    val url: String?,
    val expirationDate: String?, // LocalDate as "yyyy-MM-dd"
    val tripId: Long
)
