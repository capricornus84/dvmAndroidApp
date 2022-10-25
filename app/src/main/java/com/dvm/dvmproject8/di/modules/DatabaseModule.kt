package com.dvm.dvmproject8.di.modules

import com.dvm.dvmproject8.data.MainRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {
    @Provides
    @Singleton
    fun provideRepository() = MainRepository()
}