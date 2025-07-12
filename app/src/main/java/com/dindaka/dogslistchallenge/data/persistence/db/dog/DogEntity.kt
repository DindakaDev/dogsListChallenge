package com.dindaka.dogslistchallenge.data.persistence.db.dog

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dindaka.dogslistchallenge.data.persistence.db.DOG_TABLE_NAME

@Entity(tableName = DOG_TABLE_NAME)
data class DogEntity(
    @PrimaryKey(autoGenerate = true) val uid: Int? = null,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "age") val age: Int,
    @ColumnInfo(name = "image") val image: String,
)