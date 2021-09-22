package com.example.data

import com.example.domain.GitRepo
import com.example.domain.UseCases

class Repository(private val api: GitApi) : UseCases {
    override suspend fun getGitRepositories(since: Int): List<GitRepo>? = try {
        api.getGitRepositoriesWithIdMoreThan(since)
    } catch (e: Exception) {
        null
    }
}