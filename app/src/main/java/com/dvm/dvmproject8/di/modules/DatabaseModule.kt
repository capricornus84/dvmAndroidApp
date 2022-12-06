package com.dvm.dvmproject8.di.modules

import android.content.Context
import androidx.room.Room
import com.dvm.dvmproject8.data.MainRepository
import com.dvm.dvmproject8.data.dao.FilmDao
import com.dvm.dvmproject8.data.db.AppDatabase
//import com.dvm.dvmproject8.data.db.DatabaseHelper
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {
    @Singleton
    @Provides
    fun provideFilmDao(context: Context) =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "film_db"
        ).build().filmDao()

    @Provides
    @Singleton
    fun provideRepository(filmDao: FilmDao) = MainRepository(filmDao)
}