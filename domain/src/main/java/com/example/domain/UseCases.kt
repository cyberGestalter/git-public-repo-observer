package com.example.domain

interface UseCases {
    suspend fun getGitRepositories(since: Int): List<GitRepo>?
}