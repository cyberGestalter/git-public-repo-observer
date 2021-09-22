package com.example.gitargus.di

import com.example.gitargus.ui.GitRepoListFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ViewModelFactoryModule::class])
interface ViewModelFactoryComponent {
    fun injectIntoGitRepoList(fragment: GitRepoListFragment)
}