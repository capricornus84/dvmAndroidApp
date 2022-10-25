package com.dvm.dvmproject8.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dvm.dvmproject8.App
import com.dvm.dvmproject8.domain.Film
import com.dvm.dvmproject8.domain.Interactor
//import org.koin.core.KoinComponent
import javax.inject.Inject

//import org.koin.core.inject

class HomeFragmentViewModel : ViewModel() {
    val filmsListLiveData:  MutableLiveData<List<Film>> = MutableLiveData()
    //Инициализируем интерактор
    @Inject
    lateinit var interactor: Interactor

    init {
        App.instance.dagger.inject(this)
        interactor.getFilmsFromApi(1, object : ApiCallback {
            override fun onSuccess(films: List<Film>) {
                filmsListLiveData.postValue(films)
            }

            override fun onFailure() {
            }
        })
    }
    interface ApiCallback {
        fun onSuccess(films: List<Film>)
        fun onFailure()
    }
}