package com.dvm.dvmproject8.data

import android.content.ContentValues
import android.database.Cursor
//import com.dvm.dvmproject8.data.db.DatabaseHelper
import com.dvm.dvmproject8.data.Entity.Film
import com.dvm.dvmproject8.data.dao.FilmDao
import java.util.concurrent.Executors

class MainRepository(private val filmDao: FilmDao) {

    fun putToDb(films: List<Film>) {
        //Запросы в бд должны быть в отдельном потоке
        Executors.newSingleThreadExecutor().execute {
            filmDao.insertAll(films)
        }
    }

    fun getAllFromDB(): List<Film> {
        return filmDao.getCachedFilms()
    }
}