package com.dvm.dvmproject8.di

import com.dvm.dvmproject8.di.modules.DatabaseModule
import com.dvm.dvmproject8.di.modules.DomainModule
import com.dvm.dvmproject8.di.modules.RemoteModule
import com.dvm.dvmproject8.viewmodel.HomeFragmentViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    //Внедряем все модули, нужные для этого компонента
    modules = [
        RemoteModule::class,
        DatabaseModule::class,
        DomainModule::class
    ]
)
interface AppComponent {
    //метод для того, чтобы появилась внедрять зависимости в HomeFragmentViewModel
    fun inject(homeFragmentViewModel: HomeFragmentViewModel)
}