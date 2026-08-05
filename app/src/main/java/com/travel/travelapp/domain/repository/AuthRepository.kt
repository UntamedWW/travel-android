package com.travel.travelapp.domain.repository

import com.travel.travelapp.domain.model.User
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun login(email: String, password: String): Result<User>
    suspend fun register(email: String, password: String): Result<User>
    suspend fun logout()
    val isUserLoggedIn: Flow<Boolean>
}
