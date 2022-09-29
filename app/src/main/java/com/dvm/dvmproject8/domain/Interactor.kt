package com.dvm.dvmproject8.domain

import com.dvm.dvmproject8.data.MainRepository

class Interactor(val repo: MainRepository) {
    fun getFilmsDB(): List<Film> = repo.filmsDataBase
}