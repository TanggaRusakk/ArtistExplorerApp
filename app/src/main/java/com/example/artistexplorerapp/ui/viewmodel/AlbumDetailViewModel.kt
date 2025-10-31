package com.example.artistexplorerapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.artistexplorerapp.data.container.ArtistServerContainer
import com.example.artistexplorerapp.ui.model.AlbumModel
import com.example.artistexplorerapp.ui.model.TrackModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AlbumDetailViewModel: ViewModel() {
    private val _album = MutableStateFlow<AlbumModel?>(null)
    val album: StateFlow<AlbumModel?> = _album

    private val _trackList = MutableStateFlow<List<TrackModel>>(emptyList())
    val trackList: StateFlow<List<TrackModel>> = _trackList

    fun getAlbumDetail(albumID: String) {
        viewModelScope.launch {
            _album.value = ArtistServerContainer().artistServerRepository.GetDetailAlbumWithAlbumID(albumID)
        }
    }

    fun getTrackList(albumID: String) {
        viewModelScope.launch {
            _trackList.value = ArtistServerContainer().artistServerRepository.GetTrackListWithAlbumID(albumID)
                ?.toList() ?: emptyList()
        }
    }

    fun resetAlbumDetail() {
        _album.value = null
        _trackList.value = emptyList()
    }

    fun convertMillisToMinutesSeconds(millis: Long): String {
        val totalSeconds = millis / 1000
        val minutes = totalSeconds / 60
        val seconds = totalSeconds % 60
        return String.format("%d:%02d", minutes, seconds)
    }
}