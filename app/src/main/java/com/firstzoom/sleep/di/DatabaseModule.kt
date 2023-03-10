package com.firstzoom.sleep.di

import android.content.Context
import com.firstzoom.sleep.database.SleepDatabase
import com.firstzoom.sleep.database.SleepDatabaseDao
import com.firstzoom.sleep.repository.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

import javax.inject.Singleton




@Module
@InstallIn(SingletonComponent::class)
public class DatabaseModule {
    @Provides
    fun provideRepoDao(database: SleepDatabase): SleepDatabaseDao {
        return database.sleepDatabaseDao
    }

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): SleepDatabase {
        return SleepDatabase.getInstance(context)
    }

    @Singleton
    @Provides
    fun provideRepository(sleepDao: SleepDatabaseDao): Repository {
        return Repository(sleepDao)
    }

}