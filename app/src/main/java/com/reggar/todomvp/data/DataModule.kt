package com.reggar.todomvp.data

import android.app.Application
import android.arch.persistence.room.Room
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
open class DataModule {
    @Provides @Singleton
    internal open fun provideAppDatabase(application: Application): AppDatabase {
        return Room.databaseBuilder(application, AppDatabase::class.java, "database-name").build()
    }

    @Provides @Singleton
    internal fun provideTodoDao(appDatabase: AppDatabase) = appDatabase.todoDao()
}
