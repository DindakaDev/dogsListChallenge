package com.dindaka.dogslistchallenge.di

import com.dindaka.dogslistchallenge.domain.repository.DogRepository
import com.dindaka.dogslistchallenge.domain.usecase.GetDogsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {
    @Provides
    fun provideGetDogsUseCase(repository: DogRepository) = GetDogsUseCase(repository)
}