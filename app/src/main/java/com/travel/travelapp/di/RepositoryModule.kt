package com.travel.travelapp.di

import com.travel.travelapp.data.repository.*
import com.travel.travelapp.domain.repository.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindAuthRepository(
        authRepositoryImpl: AuthRepositoryImpl
    ): AuthRepository

    @Binds
    @Singleton
    abstract fun bindTravelRepository(
        travelRepositoryImpl: TravelRepositoryImpl
    ): TravelRepository

    @Binds
    @Singleton
    abstract fun bindExpenseRepository(
        expenseRepositoryImpl: ExpenseRepositoryImpl
    ): ExpenseRepository

    @Binds
    @Singleton
    abstract fun bindItineraryRepository(
        itineraryRepositoryImpl: ItineraryRepositoryImpl
    ): ItineraryRepository

    @Binds
    @Singleton
    abstract fun bindPackingRepository(
        packingRepositoryImpl: PackingRepositoryImpl
    ): PackingRepository

    @Binds
    @Singleton
    abstract fun bindDocumentRepository(
        documentRepositoryImpl: DocumentRepositoryImpl
    ): DocumentRepository
}
