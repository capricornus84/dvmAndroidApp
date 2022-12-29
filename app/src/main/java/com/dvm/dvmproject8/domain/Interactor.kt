package com.dvm.dvmproject8.domain

import io.reactivex.rxjava3.core.Observable
import androidx.lifecycle.LiveData
import com.dvm.dvmproject8.API
import com.dvm.dvmproject8.data.Entity.Film
//import com.dvm.remote_module.entity.TmdbResults
import com.dvm.dvmproject8.data.MainRepository
//import com.dvm.dvmproject8.data.TmdbApi
import com.dvm.dvmproject8.data.preferenes.PreferenceProvider
import com.dvm.dvmproject8.utils.Converter
import com.dvm.dvmproject8.viewmodel.HomeFragmentViewModel
//import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.BehaviorSubject
import io.reactivex.rxjava3.kotlin.subscribeBy
import com.dvm.remote_module.TmdbApi


class Interactor(private val repo: MainRepository, private val retrofitService: TmdbApi, private val preferences: PreferenceProvider) {
    var progressBarState: BehaviorSubject<Boolean> = BehaviorSubject.create()
    fun getFilmsFromApi(page: Int) {
        //Показываем ProgressBar
        progressBarState.onNext(true)
        //Метод getDefaultCategoryFromPreferences() будет нам получать при каждом запросе нужный нам список фильмов

                retrofitService.getFilms(getDefaultCategoryFromPreferences(), API.KEY, "ru-RU", page)
                    .subscribeOn(Schedulers.io())
                    .map {
                        Converter.convertApiListToDTOList(it.tmdbFilms)
            }
                    .subscribeBy(
                        onError = {
                            progressBarState.onNext(false)
                        },
                        onNext = {
                            progressBarState.onNext(false)
                            repo.putToDb(it)
                        }
                    )
    }
    //Метод для сохранения настроек
    fun saveDefaultCategoryToPreferences(category: String) {
        preferences.saveDefaultCategory(category)
    }
    //Метод для получения настроек
    fun getDefaultCategoryFromPreferences() = preferences.geDefaultCategory()

    fun getFilmsFromDB(): Observable<List<Film>> = repo.getAllFromDB()

    fun getSearchResultFromApi(search: String): Observable<List<Film>> = retrofitService.getFilmFromSearch(API.KEY, "ru-RU", search, 1)
        .map {
            Converter.convertApiListToDTOList(it.tmdbFilms)
        }
}