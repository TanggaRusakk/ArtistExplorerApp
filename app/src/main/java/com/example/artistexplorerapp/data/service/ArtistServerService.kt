package com.example.artistexplorerapp.data.service

import com.example.artistexplorerapp.data.dto.ResponseAlbums
import com.example.artistexplorerapp.data.dto.ResponseArtist
import com.example.artistexplorerapp.data.dto.ResponseDetailAlbum
import com.example.artistexplorerapp.data.dto.ResponseTracks
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ArtistServerService {
    @GET("api/v1/json/123/search.php")
    suspend fun GetArtistWithArtistName(
        @Query("s") artistName: String
    ): Response<ResponseArtist>

    @GET("api/v1/json/123/searchalbum.php")
    suspend fun GetAlbumsWithArtistName(
        @Query("s") artistName: String
    ): Response<ResponseAlbums>

    @GET("api/v1/json/123/album.php")
    suspend fun GetDetailAlbumWithAlbumID(
        @Query("m") albumID: String
    ): Response<ResponseDetailAlbum>

    @GET("api/v1/json/123/track.php")
    suspend fun GetTracksWithAlbumID(
        @Query("m") albumID: String
    ): Response<ResponseTracks>
}