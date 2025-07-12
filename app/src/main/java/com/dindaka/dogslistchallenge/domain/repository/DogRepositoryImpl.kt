package com.dindaka.dogslistchallenge.domain.repository

import com.dindaka.dogslistchallenge.data.mappers.toDomain
import com.dindaka.dogslistchallenge.data.mappers.toEntity
import com.dindaka.dogslistchallenge.data.model.DogData
import com.dindaka.dogslistchallenge.data.persistence.db.dog.DogDao
import com.dindaka.dogslistchallenge.data.remote.ApiService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DogRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val dogDao: DogDao,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : DogRepository {
    override suspend fun getDogs(): Result<List<DogData>> {
        return withContext(dispatcher) {
            val localData = dogDao.getDogs()

            if (localData.isNotEmpty()) {
                return@withContext Result.success(localData.map { it.toDomain() })
            } else {
                callGetDogs()
            }
        }
    }

    override suspend fun callGetDogs(): Result<List<DogData>> {
        return try {
            val dataApi = apiService.getDogs()
            dogDao.insertAll(dataApi.map { it.toEntity() })
            Result.success(dataApi.map { it.toDomain() })
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}