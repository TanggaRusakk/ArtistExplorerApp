package com.example.artistexplorerapp.ui.model

data class AlbumModel(
    val id: String = "",
    val name: String = "",
    val year: String = "",
    val genre: String = "",
    val description: String = "",
    val imagePath: String = "",
    val isError: Boolean = false,
    val errorMessage: String = ""
)
