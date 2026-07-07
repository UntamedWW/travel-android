package com.travel.travelapp.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class DocumentRequest(
    val name: String,
    val number: String?,
    val type: String?,
    val url: String?,
    val expirationDate: String?, // LocalDate as "yyyy-MM-dd"
    val tripId: Long
)
