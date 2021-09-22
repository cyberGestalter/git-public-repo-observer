package com.example.domain

import com.squareup.moshi.Json

data class GitRepo(
    val id: Int,
    var name: String,
    var owner: Owner,
    @Json(name = "html_url")
    var htmlUrl: String,
    var description: String?
)

data class Owner(
    @Json(name = "login")
    var ownerName: String?,
    @Json(name = "avatar_url")
    var ownerPhoto: String?
)
