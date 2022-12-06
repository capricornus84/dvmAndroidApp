package com.dvm.dvmproject8.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dvm.dvmproject8.data.Entity.Film
import com.dvm.dvmproject8.data.dao.FilmDao

@Database(entities = [Film::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun filmDao(): FilmDao
}