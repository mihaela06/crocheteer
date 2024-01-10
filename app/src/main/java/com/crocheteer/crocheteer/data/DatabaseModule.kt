package com.crocheteer.crocheteer.data

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabaseYarnStashRepository(yarnDao: YarnDao): YarnStashRepository {
        return DatabaseYarnStashRepository(yarnDao)
    }

    @Provides
    @Singleton
    fun provideYarnDatabase(@ApplicationContext context: Context): YarnDatabase {
        return synchronized(this) {
            Room.databaseBuilder(context, YarnDatabase::class.java, "yarn_database.db")
                .createFromAsset("yarn_database.db")
                .fallbackToDestructiveMigration()
                .build()
        }
    }

    @Provides
    @Singleton
    fun provideDao(yarnDatabase: YarnDatabase) = yarnDatabase.yarnDao()
}