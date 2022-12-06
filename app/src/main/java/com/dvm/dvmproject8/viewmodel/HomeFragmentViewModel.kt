package com.dvm.dvmproject8.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dvm.dvmproject8.App
import com.dvm.dvmproject8.data.Entity.Film
import com.dvm.dvmproject8.domain.Interactor
import java.util.concurrent.Executors
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.channels.Channel
//import org.koin.core.KoinComponent
import javax.inject.Inject

//import org.koin.core.inject

class HomeFragmentViewModel : ViewModel() {
    //val showProgressBar: MutableLiveData<Boolean> = MutableLiveData()

    //Инициализируем интерактор
    @Inject
    lateinit var interactor: Interactor
    val filmsListData: Flow<List<Film>>
    val showProgressBar: Channel<Boolean>

    init {
        App.instance.dagger.inject(this)
        showProgressBar = interactor.progressBarState
        filmsListData = interactor.getFilmsFromDB()
        getFilms()
    }

    fun getFilms() {
        interactor.getFilmsFromApi(1)
    }

}