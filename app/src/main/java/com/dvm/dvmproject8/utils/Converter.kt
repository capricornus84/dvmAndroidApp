package com.dvm.dvmproject8.utils

//import com.dvm.remote_module.entity.TmdbFilm
import com.dvm.dvmproject8.data.Entity.Film
import com.dvm.remote_module.entity.TmdbFilm

object Converter {
    fun convertApiListToDTOList(list: List<TmdbFilm>?): List<Film> {
        val result = mutableListOf<Film>()
        list?.forEach {
            result.add(Film(
                title = it.title,
                poster = it.posterPath,
                description = it.overview,
                rating = it.voteAverage,
                isInFavorites = false
            ))
        }
        return result
    }
}