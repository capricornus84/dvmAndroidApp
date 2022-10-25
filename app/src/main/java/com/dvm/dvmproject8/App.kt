package com.dvm.dvmproject8

import android.app.Application
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory.Companion.instance
import com.dvm.dvmproject8.data.ApiConstants
import com.dvm.dvmproject8.data.MainRepository
import com.dvm.dvmproject8.data.TmdbApi
import com.dvm.dvmproject8.di.AppComponent
import com.dvm.dvmproject8.di.DaggerAppComponent

class App : Application() {
    lateinit var dagger: AppComponent

    override fun onCreate() {
        super.onCreate()
        instance = this
        //Создаем компонент
        dagger = DaggerAppComponent.create()
        }
    companion object {
        lateinit var instance: App
            private set
    }

}