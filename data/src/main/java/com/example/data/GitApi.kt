package com.example.data

import com.example.domain.GitRepo
import retrofit2.http.GET
import retrofit2.http.Query

interface GitApi {
    @GET("/repositories")
    suspend fun getGitRepositoriesWithIdMoreThan(@Query("since") since: Int): List<GitRepo>?
}