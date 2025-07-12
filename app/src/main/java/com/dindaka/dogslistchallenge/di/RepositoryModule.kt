package com.dindaka.dogslistchallenge.di

import com.dindaka.dogslistchallenge.data.persistence.db.dog.DogDao
import com.dindaka.dogslistchallenge.data.remote.ApiService
import com.dindaka.dogslistchallenge.domain.repository.DogRepository
import com.dindaka.dogslistchallenge.domain.repository.DogRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideCityRepository(
        apiService: ApiService,
        dogDao: DogDao
    ): DogRepository =
        DogRepositoryImpl(apiService, dogDao)
}