package com.example.artistexplorerapp.data.container

import com.example.artistexplorerapp.data.repository.ArtistServerRepositories
import com.example.artistexplorerapp.data.service.ArtistServerService
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ArtistServerContainer {
    companion object {
        val BASE_URL = "https://www.theaudiodb.com/"
    }

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
        .baseUrl(BASE_URL)
        .build()

    private val retrofitService: ArtistServerService by lazy {
        retrofit.create(ArtistServerService::class.java)
    }

    val artistServerRepository: ArtistServerRepositories by lazy {
        ArtistServerRepositories(retrofitService)
    }
}