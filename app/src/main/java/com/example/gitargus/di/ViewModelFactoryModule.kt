package com.example.gitargus.di

import com.example.data.GitApi
import com.example.data.Repository
import com.example.domain.UseCases
import com.example.gitargus.viewmodel.ViewModelFactory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [GitApiModule::class])
class ViewModelFactoryModule {
    @Singleton
    @Provides
    fun viewModelFactory(useCases: UseCases): ViewModelFactory = ViewModelFactory
        .getFactory(useCases)

    @Singleton
    @Provides
    fun useCases(api: GitApi): UseCases = Repository(api)

}