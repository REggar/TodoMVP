package com.reggar.todomvp.data

import android.app.Application
import android.arch.persistence.room.Room

class TestDataModule : DataModule() {
    override fun provideAppDatabase(application: Application): AppDatabase {
        return Room.inMemoryDatabaseBuilder(application, AppDatabase::class.java).build()
    }
}