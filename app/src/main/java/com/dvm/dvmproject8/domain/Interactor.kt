package com.dvm.dvmproject8.domain

import com.dvm.dvmproject8.API
import com.dvm.dvmproject8.data.Entity.TmdbResults
import com.dvm.dvmproject8.data.MainRepository
import com.dvm.dvmproject8.data.TmdbApi
import com.dvm.dvmproject8.data.preferenes.PreferenceProvider
import com.dvm.dvmproject8.utils.Converter
import com.dvm.dvmproject8.viewmodel.HomeFragmentViewModel
import retrofit2.Call
import retrofit2.Callback
//import retrofit2.Converter
import retrofit2.Response

class Interactor(private val repo: MainRepository, private val retrofitService: TmdbApi, private val preferences: PreferenceProvider) {
    fun getFilmsFromApi(page: Int, callback: HomeFragmentViewModel.ApiCallback) {
        //Метод getDefaultCategoryFromPreferences() будет нам получать при каждом запросе нужный нам список фильмов
        retrofitService.getFilms(getDefaultCategoryFromPreferences(), API.KEY, "ru-RU", page).enqueue(object : Callback<TmdbResults> {
            override fun onResponse(call: Call<TmdbResults>, response: Response<TmdbResults>) {
                //При успехе мы вызываем метод передаем onSuccess и в этот коллбэк список фильмов
                callback.onSuccess(Converter.convertApiListToDTOList(response.body()?.tmdbFilms))
            }

            override fun onFailure(call: Call<TmdbResults>, t: Throwable) {
                //В случае провала вызываем другой метод коллбека
                callback.onFailure()
            }
        })
    }
    //Метод для сохранения настроек
    fun saveDefaultCategoryToPreferences(category: String) {
        preferences.saveDefaultCategory(category)
    }
    //Метод для получения настроек
    fun getDefaultCategoryFromPreferences() = preferences.geDefaultCategory()
}