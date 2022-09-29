package com.dvm.dvmproject8.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dvm.dvmproject8.App
import com.dvm.dvmproject8.domain.Film
import com.dvm.dvmproject8.domain.Interactor

class HomeFragmentViewModel : ViewModel() {
    val filmsListLiveData = MutableLiveData<List<Film>>()
    //Инициализируем интерактор
    private var interactor: Interactor = App.instance.interactor
    init {
        val films = interactor.getFilmsDB()
        filmsListLiveData.postValue(films)
    }
}