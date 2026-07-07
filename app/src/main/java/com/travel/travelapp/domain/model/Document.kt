package com.travel.travelapp.domain.model

data class Document(
    val id: Long,
    val name: String,
    val type: String?,
    val number: String?,
    val url: String?,
    val expirationDate: String?
)
