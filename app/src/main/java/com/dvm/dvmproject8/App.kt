package com.dvm.dvmproject8

import android.app.Application
import com.dvm.dvmproject8.di.AppComponent
import com.dvm.dvmproject8.di.DaggerAppComponent
import com.dvm.dvmproject8.di.modules.DatabaseModule
import com.dvm.dvmproject8.di.modules.DomainModule
//import com.dvm.dvmproject8.di.modules.RemoteModule
import com.dvm.remote_module.DaggerRemoteComponent

class App : Application() {
    lateinit var dagger: AppComponent

    override fun onCreate() {
        super.onCreate()
        instance = this
        //Создаем компонент
        val remoteProvider = DaggerRemoteComponent.create()
        dagger = DaggerAppComponent.builder()
            //.remoteModule(RemoteModule())
            .remoteProvider(remoteProvider)
            .databaseModule(DatabaseModule())
            .domainModule(DomainModule(this))
            .build()
    }

    companion object {
        lateinit var instance: App
            private set
    }

}