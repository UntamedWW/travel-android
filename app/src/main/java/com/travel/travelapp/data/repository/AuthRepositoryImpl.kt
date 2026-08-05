package com.travel.travelapp.data.repository

import com.travel.travelapp.data.local.DataStoreManager
import com.travel.travelapp.data.remote.AuthApi
import com.travel.travelapp.data.remote.dto.AuthRequest
import com.travel.travelapp.data.remote.dto.UserRequest
import com.travel.travelapp.domain.model.User
import com.travel.travelapp.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val authApi: AuthApi,
    private val dataStoreManager: DataStoreManager
) : AuthRepository {

    override suspend fun login(email: String, password: String): Result<User> {
        return try {
            val response = authApi.login(AuthRequest(email, password))
            dataStoreManager.saveAuthToken(response.token)
            Result.success(User(id = response.id, email = response.email))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun register(email: String, password: String): Result<User> {
        return try {
            val response = authApi.register(UserRequest(email, password))
            dataStoreManager.saveAuthToken(response.token)
            Result.success(User(id = response.id, email = response.email))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun logout() {
        dataStoreManager.clearAuthToken()
    }

    override val isUserLoggedIn: Flow<Boolean> = dataStoreManager.authToken.map { token ->
        !token.isNullOrEmpty()
    }
}