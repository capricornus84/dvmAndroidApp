package com.dvm.remote_module

interface RemoteProvider {
    fun provideRemote(): TmdbApi
}