package com.dindaka.dogslistchallenge.data.mappers

import com.dindaka.dogslistchallenge.data.model.DogData
import com.dindaka.dogslistchallenge.data.persistence.db.dog.DogEntity
import com.dindaka.dogslistchallenge.data.remote.dto.DogDto

fun DogDto.toDomain(): DogData = DogData(dogName, description, age, image)

fun DogEntity.toDomain(): DogData = DogData(name, description, age, image)

fun DogDto.toEntity(): DogEntity = DogEntity(
    name = dogName,
    description = description,
    age = age,
    image = image
)