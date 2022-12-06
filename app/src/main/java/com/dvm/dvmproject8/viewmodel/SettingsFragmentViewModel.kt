package com.dvm.dvmproject8.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory.Companion.instance
import com.dvm.dvmproject8.App
import com.dvm.dvmproject8.domain.Interactor
import javax.inject.Inject

class SettingsFragmentViewModel : ViewModel() {
    //Инжектим интерактор
    @Inject
    lateinit var interactor: Interactor
    val categoryPropertyLifeData: MutableLiveData<String> = MutableLiveData()

    init {
        App.instance.dagger.inject(this)
        //Получаем категорию при иницализации, чтобы у нас сразу подтягивалась категория
        getCategoryProperty()
    }

    private fun getCategoryProperty() {
        //Кладем каттегорию в LiveData
        categoryPropertyLifeData.value = interactor.getDefaultCategoryFromPreferences()
    }

    fun putCategoryProperty(category: String) {
        //Сохраняем в настройки
        interactor.saveDefaultCategoryToPreferences(category)
        //И сразу забираем,чтобы сохранить состояние в модели
        getCategoryProperty()
    }
}