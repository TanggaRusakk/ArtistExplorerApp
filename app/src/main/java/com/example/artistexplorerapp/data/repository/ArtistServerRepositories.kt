package com.example.artistexplorerapp.data.repository

import android.util.Log
import com.example.artistexplorerapp.data.dto.Album
import com.example.artistexplorerapp.data.service.ArtistServerService
import com.example.artistexplorerapp.ui.model.AlbumModel
import com.example.artistexplorerapp.ui.model.ArtistModel
import com.example.artistexplorerapp.ui.model.TrackModel
import okio.IOException

class ArtistServerRepositories(
    private val service: ArtistServerService
) {
    suspend fun GetArtistWithArtistName(artistName: String): ArtistModel {
        try {
            val response = service.GetArtistWithArtistName(artistName)

            val artist = response.body()

            if (artist != null) {
                val dataArtist = ArtistModel(
                    name = artist.artists?.get(0)?.strArtist ?: "",
                    genre = artist.artists?.get(0)?.strGenre ?: "",
                    imagePath = artist.artists?.get(0)?.strArtistThumb ?: "",
                    biografi = artist.artists?.get(0)?.strBiographyEN ?: ""
                )

                return dataArtist
            } else {
                val dataArtist = ArtistModel(
                    isError = true,
                    errorMessage = "Artis tidak ditemukan"
                )

                return dataArtist
            }
        } catch (e: IOException) {
            val dataArtist = ArtistModel(
                isError = true,
                errorMessage = "Tidak ada koneksi internet"
            )

            return dataArtist
        } catch (e: Exception) {
            val dataArtist = ArtistModel(
                isError = true,
                errorMessage = e.message ?: "Terjadi error yang tidak diketahui"
            )

            return dataArtist
        }
    }

    suspend fun GetAlbumListWithArtistName(artistName: String): List<AlbumModel>? {
        try {
            val response = service.GetAlbumsWithArtistName(artistName)

            val albums = response.body()

            val dataAlbums = mutableListOf<AlbumModel>()

            if (albums != null && albums.album?.isNotEmpty() == true) {
                for (album in albums.album!!) {
                    val dataAlbum = AlbumModel(
                        id = album.idAlbum ?: "",
                        name = album.strAlbum ?: "",
                        year = album.intYearReleased ?: "",
                        genre = album.strGenre ?: "",
                        description = album.strDescriptionEN ?: "",
                        imagePath = album.strAlbumThumb ?: ""
                    )

                    dataAlbums.add(dataAlbum)
                }
                return dataAlbums
            } else {
                return null
            }
        } catch (e: IOException) {
            return null
        } catch (e: Exception) {
            return null
        }
    }

    suspend fun GetDetailAlbumWithAlbumID(albumID: String): AlbumModel {
        try {
            val response = service.GetDetailAlbumWithAlbumID(albumID)

            val album = response.body()

            if (album != null && album.album?.isNotEmpty() == true) {
                val albumData = album.album[0]
                val dataAlbum = AlbumModel(
                    id = albumData.idAlbum ?: "",
                    name = albumData.strAlbum ?: "",
                    year = albumData.intYearReleased ?: "",
                    genre = albumData.strGenre ?: "",
                    description = (albumData.strDescription ?: albumData.strDescriptionEN ?: "") as String,
                    imagePath = (albumData.strAlbumThumb ?: "") as String
                )
                return dataAlbum
            } else {
                val dataAlbum = AlbumModel(
                    isError = true,
                    errorMessage = "Album tidak ditemukan"
                )
                return dataAlbum
            }
        } catch (e: IOException) {
            val dataAlbum = AlbumModel(
                isError = true,
                errorMessage = "Tidak ada koneksi internet"
            )
            return dataAlbum
        } catch (e: Exception) {
            val dataAlbum = AlbumModel(
                isError = true,
                errorMessage = e.message ?: "Terjadi error yang tidak diketahui"
            )
            return dataAlbum
        }
    }

    suspend fun GetTrackListWithAlbumID(albumID: String): List<TrackModel>? {
        try {
            val response = service.GetTracksWithAlbumID(albumID)

            val tracks = response.body()

            val dataTracks = mutableListOf<TrackModel>()

            if (tracks != null) {
                val dataTracks = mutableListOf<TrackModel>()

                for (track in tracks.track!!) {
                    val dataTrack = TrackModel(
                        name = track.strTrack ?: "",
                        duration = track.intDuration.toLong()
                    )
                    dataTracks.add(dataTrack)
                }
                return dataTracks
            } else {
                return null
            }
        } catch (e: IOException) {
            return null
        } catch (e: Exception) {
            return null
        }
    }
}