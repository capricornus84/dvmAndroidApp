package com.dvm.dvmproject8.viewmodel

import androidx.lifecycle.ViewModel
import com.dvm.dvmproject8.App
import com.dvm.dvmproject8.data.Entity.Film
import com.dvm.dvmproject8.domain.Interactor
import io.reactivex.rxjava3.subjects.BehaviorSubject
import javax.inject.Inject
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import com.dvm.dvmproject8.utils.AutoDisposable
import com.dvm.dvmproject8.utils.addTo
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.subjects.PublishSubject

//import org.koin.core.inject

class HomeFragmentViewModel : ViewModel() {
    //val showProgressBar: MutableLiveData<Boolean> = MutableLiveData()
    //Инициализируем интерактор
    @Inject
    lateinit var interactor: Interactor
    val filmsListData: Observable<List<Film>>
    val showProgressBar: BehaviorSubject<Boolean>

    init {
        App.instance.dagger.inject(this)
        showProgressBar = interactor.progressBarState
        filmsListData = interactor.getFilmsFromDB()
        getFilms()
    }

    fun getFilms() {
        interactor.getFilmsFromApi(1)
    }
    fun getSearchResult(search: String) = interactor.getSearchResultFromApi(search)

}