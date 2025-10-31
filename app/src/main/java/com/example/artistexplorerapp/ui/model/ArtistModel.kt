package com.example.artistexplorerapp.ui.model

data class ArtistModel(
    val name: String = "",
    val genre: String = "",
    val imagePath: String = "",
    val biografi: String = "",
    val isError: Boolean = false,
    val errorMessage: String = ""
)
