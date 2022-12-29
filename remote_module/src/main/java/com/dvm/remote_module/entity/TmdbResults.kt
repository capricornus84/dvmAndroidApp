package com.dvm.remote_module.entity

import com.google.gson.annotations.SerializedName

data class TmdbResults(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val tmdbFilms: List<com.dvm.remote_module.entity.TmdbFilm>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)