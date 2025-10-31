package com.example.artistexplorerapp.ui.viewmodel

import android.graphics.Movie
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.artistexplorerapp.data.container.ArtistServerContainer
import com.example.artistexplorerapp.ui.model.AlbumModel
import com.example.artistexplorerapp.ui.model.ArtistModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ArtistViewModel: ViewModel() {
    private val _artist = MutableStateFlow<ArtistModel?>(null)

    val artist: StateFlow<ArtistModel?> = _artist

    private val _albumList = MutableStateFlow<List<AlbumModel>>(emptyList())

    val albumList: StateFlow<List<AlbumModel>> = _albumList

    init {
        loadArtist("John Mayer")
        loadAlbumList("John Mayer")
    }

    fun loadArtist(artistName: String) {
        viewModelScope.launch {
            _artist.value = ArtistServerContainer().artistServerRepository.GetArtistWithArtistName(artistName)
        }
    }

    fun loadAlbumList(artistName: String) {
        viewModelScope.launch {
            _albumList.value = ArtistServerContainer().artistServerRepository.GetAlbumListWithArtistName(artistName)
                ?.toList() ?: emptyList()
        }
    }
}