package com.dindaka.dogslistchallenge.data.remote.dto

import com.squareup.moshi.JsonClass

typealias DogsDto = List<DogDto>

@JsonClass(generateAdapter = true)
data class DogDto(
    val dogName: String,
    val description: String,
    val age: Int,
    val image: String
)