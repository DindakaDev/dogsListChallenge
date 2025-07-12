package com.dindaka.dogslistchallenge.data.persistence.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dindaka.dogslistchallenge.data.persistence.db.dog.DogDao
import com.dindaka.dogslistchallenge.data.persistence.db.dog.DogEntity

@Database(
    entities = [
        DogEntity::class,
    ],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun dogDao(): DogDao
}
