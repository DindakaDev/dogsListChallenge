package com.dindaka.dogslistchallenge.di

import android.content.Context
import androidx.room.Room
import com.dindaka.dogslistchallenge.data.persistence.db.AppDatabase
import com.dindaka.dogslistchallenge.data.persistence.db.DATABASE_NAME
import com.dindaka.dogslistchallenge.data.persistence.db.dog.DogDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext applicationContext: Context): AppDatabase {
        return Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideDogDao(appDatabase: AppDatabase): DogDao {
        return appDatabase.dogDao()
    }
}