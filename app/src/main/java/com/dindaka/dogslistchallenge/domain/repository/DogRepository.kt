package com.dindaka.dogslistchallenge.domain.repository

import com.dindaka.dogslistchallenge.data.model.DogData

interface DogRepository {
    suspend fun getDogs(): Result<List<DogData>>

    suspend fun callGetDogs(): Result<List<DogData>>
}