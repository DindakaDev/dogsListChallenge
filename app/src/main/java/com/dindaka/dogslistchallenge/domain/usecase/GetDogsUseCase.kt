package com.dindaka.dogslistchallenge.domain.usecase

import com.dindaka.dogslistchallenge.data.model.DogData
import com.dindaka.dogslistchallenge.domain.repository.DogRepository

class GetDogsUseCase(
    private val repository: DogRepository
) {
    suspend operator fun invoke(forceRefresh: Boolean = false): Result<List<DogData>> {
        return if (forceRefresh) {
            repository.callGetDogs()
        } else {
            repository.getDogs()
        }
    }
}
