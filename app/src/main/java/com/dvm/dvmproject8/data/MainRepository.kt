package com.dvm.dvmproject8.data

import android.content.ContentValues
import android.database.Cursor
import androidx.lifecycle.LiveData
//import com.dvm.dvmproject8.data.db.DatabaseHelper
import com.dvm.dvmproject8.data.Entity.Film
import com.dvm.dvmproject8.data.dao.FilmDao
import java.util.concurrent.Executors
import kotlinx.coroutines.flow.Flow

class MainRepository(private val filmDao: FilmDao) {

    fun putToDb(films: List<Film>) {
        filmDao.insertAll(films)
    }

    fun getAllFromDB(): Flow<List<Film>> = filmDao.getCachedFilms()
}