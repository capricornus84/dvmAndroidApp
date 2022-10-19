package com.dvm.dvmproject8

import android.app.Application
import com.dvm.dvmproject8.data.ApiConstants
import com.dvm.dvmproject8.data.MainRepository
import com.dvm.dvmproject8.data.TmdbApi
import com.dvm.dvmproject8.di.DI
import com.dvm.dvmproject8.domain.Interactor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            //Прикрепляем контекст
            androidContext(this@App)
            //(Опционально) подключаем зависимость
            androidLogger()
            //Инициализируем модули
            modules(listOf(DI.mainModule))
        }
    }
}