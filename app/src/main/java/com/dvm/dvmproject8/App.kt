package com.dvm.dvmproject8

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import com.dvm.dvmproject8.di.AppComponent
import com.dvm.dvmproject8.di.DaggerAppComponent
import com.dvm.dvmproject8.di.modules.DatabaseModule
import com.dvm.dvmproject8.di.modules.DomainModule
import com.dvm.dvmproject8.view.notifications.NotificationConstants.CHANNEL_ID
//import com.dvm.dvmproject8.di.modules.RemoteModule
import com.dvm.remote_module.DaggerRemoteComponent

class App : Application() {
    lateinit var dagger: AppComponent
    var isPromoShown = false

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

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //Задаем имя, описание и важность канала
            val name = "WatchLaterChannel"
            val descriptionText = "FilmsSearch notification Channel"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            //Создаем канал, передав в параметры его ID(строка), имя(строка), важность(константа)
            val mChannel = NotificationChannel(CHANNEL_ID, name, importance)
            //Отдельно задаем описание
            mChannel.description = descriptionText
            //Получаем доступ к менеджеру нотификаций
            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            //Регистрируем канал
            notificationManager.createNotificationChannel(mChannel)
        }
    }

    companion object {
        lateinit var instance: App
            private set
    }

}