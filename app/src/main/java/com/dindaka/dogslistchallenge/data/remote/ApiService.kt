package com.dindaka.dogslistchallenge.data.remote

import com.dindaka.dogslistchallenge.data.remote.dto.DogsDto
import retrofit2.http.GET

interface ApiService {
    @GET("api/1151549092634943488")
    suspend fun getDogs(
    ): DogsDto
}