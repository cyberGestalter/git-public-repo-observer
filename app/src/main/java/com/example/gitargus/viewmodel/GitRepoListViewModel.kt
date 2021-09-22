package com.example.gitargus.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.GitRepo
import com.example.domain.UseCases
import kotlinx.coroutines.launch

class GitRepoListViewModel(private val repository: UseCases) : ViewModel() {
    private val _gitRepos = MutableLiveData<List<GitRepo>?>()
    val gitRepos: LiveData<List<GitRepo>?>
        get() = _gitRepos

    private var lastRepoId = 0

    init {
        loadMoreGitRepos()
    }

    fun loadMoreGitRepos() {
        viewModelScope.launch {
            val newRepos = repository.getGitRepositories(lastRepoId)
            val resultList = mutableListOf<GitRepo>()
            if (newRepos != null) {
                _gitRepos.value?.forEach { element ->
                    resultList.add(element)
                }
                resultList.addAll(newRepos)
            }
            if (resultList.isNotEmpty()) {
                lastRepoId = resultList.last().id
            }
            _gitRepos.value = resultList
        }
    }

    fun loadReposAgain() {
        lastRepoId = 0
        loadMoreGitRepos()
    }
}