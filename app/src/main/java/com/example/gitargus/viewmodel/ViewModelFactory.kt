package com.example.gitargus.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.domain.UseCases

class ViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GitRepoListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return GitRepoListViewModel(repository) as T
        }

        throw IllegalArgumentException()
    }

    companion object {
        private lateinit var repository: UseCases
        fun getFactory(useCases: UseCases) = ViewModelFactory().also { repository = useCases }
    }
}