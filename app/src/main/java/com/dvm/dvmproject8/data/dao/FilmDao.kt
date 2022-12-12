package com.dvm.dvmproject8.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dvm.dvmproject8.data.Entity.Film
import kotlinx.coroutines.flow.Flow

//Помечаем, что это не просто интерфейс а Dao объект
@Dao
interface FilmDao {
    //Запрос на всю таблицу
    @Query("SELECT * FROM cached_films")
    fun getCachedFilms(): Flow<List<Film>>

    //Кладем списком в БД, в случае конфликта, перезаписываем
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<Film>)
}