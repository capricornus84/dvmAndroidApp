package com.dvm.dvmproject8.di.modules

import com.dvm.dvmproject8.data.MainRepository
import com.dvm.dvmproject8.data.TmdbApi
import com.dvm.dvmproject8.domain.Interactor
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DomainModule {
    @Singleton
    @Provides
    fun provideInteractor(repository: MainRepository, tmdbApi: TmdbApi) = Interactor(repo = repository, retrofitService = tmdbApi)
}