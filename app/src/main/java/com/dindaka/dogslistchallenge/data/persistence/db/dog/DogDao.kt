package com.dindaka.dogslistchallenge.data.persistence.db.dog

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dindaka.dogslistchallenge.data.persistence.db.DOG_TABLE_NAME

@Dao
interface DogDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(cities: List<DogEntity>)

    @Query("SELECT * FROM $DOG_TABLE_NAME")
    suspend fun getDogs(): List<DogEntity>
}