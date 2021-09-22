package com.example.gitargus.di

import com.example.data.GitApi
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
class GitApiModule {
    @Singleton
    @Provides
    fun gitApi(retrofit: Retrofit): GitApi =
        retrofit.create(GitApi::class.java)

    @Singleton
    @Provides
    fun retrofit(moshiConverterFactory: MoshiConverterFactory) = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(moshiConverterFactory)
        .build()

    @Singleton
    @Provides
    fun moshiConverterFactory(moshi: Moshi): MoshiConverterFactory =
        MoshiConverterFactory.create(moshi)

    @Singleton
    @Provides
    fun moshi(): Moshi = Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory())
        .build()

    companion object {
        private const val BASE_URL = "https://api.github.com"
    }
}