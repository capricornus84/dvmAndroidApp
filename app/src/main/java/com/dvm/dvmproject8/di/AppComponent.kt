package com.dvm.dvmproject8.di

import com.dvm.dvmproject8.di.modules.DatabaseModule
import com.dvm.dvmproject8.di.modules.DomainModule
//import com.dvm.dvmproject8.di.modules.RemoteModule
import com.dvm.dvmproject8.viewmodel.HomeFragmentViewModel
import com.dvm.dvmproject8.viewmodel.SettingsFragmentViewModel
import com.dvm.remote_module.RemoteProvider
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    //Внедряем все модули, нужные для этого компонента
    dependencies = [RemoteProvider::class],
    modules = [
        //RemoteModule::class,
        DatabaseModule::class,
        DomainModule::class
    ]
)
interface AppComponent {
    //метод для того, чтобы появилась возможность внедрять зависимости в HomeFragmentViewModel
    fun inject(homeFragmentViewModel: HomeFragmentViewModel)
    //метод для того, чтобы появилась возможность внедрять зависимости в SettingsFragmentViewModel
    fun inject(settingsFragmentViewModel: SettingsFragmentViewModel)
}