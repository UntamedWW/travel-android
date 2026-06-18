package com.travel.travelapp.di

import com.travel.travelapp.data.repository.TravelRepositoryImpl
import com.travel.travelapp.domain.repository.TravelRepository
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
    abstract fun bindTravelRepository(
        travelRepositoryImpl: TravelRepositoryImpl
    ): TravelRepository
}
