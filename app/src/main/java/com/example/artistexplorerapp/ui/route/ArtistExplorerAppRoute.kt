package com.example.artistexplorerapp.ui.route

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.artistexplorerapp.ui.view.AlbumDetailView
import com.example.artistexplorerapp.ui.view.ArtistExplolerAppView
import com.example.artistexplorerapp.ui.viewmodel.AlbumDetailViewModel
import com.example.artistexplorerapp.ui.viewmodel.ArtistViewModel


enum class ArtistExplorerAppView() {
    ArtistExplorerApp,
    AlbumDetail
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArtistExplorerAppRoute(
    artistViewModel: ArtistViewModel = viewModel(),
    albumDetailViewModel: AlbumDetailViewModel = viewModel(),
) {
    val navController = rememberNavController()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val currentRoute = currentDestination?.route
    val currentView = ArtistExplorerAppView.entries.find {
        currentRoute?.startsWith(it.name) == true
    }

    Scaffold(
        topBar = {
            ArtistExplorerTopBar(
                currentView = currentView,
                artistViewModel = artistViewModel,
                albumDetailViewModel = albumDetailViewModel,
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = ArtistExplorerAppView.ArtistExplorerApp.name
        ) {
            composable(
                route = ArtistExplorerAppView.ArtistExplorerApp.name
            ) {
                ArtistExplolerAppView(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentPadding = innerPadding,
                    viewModel = artistViewModel,
                    albumDetailViewModel = albumDetailViewModel,
                    navController = navController
                )
            }
            composable(
                route  = ArtistExplorerAppView.AlbumDetail.name + "/{albumID}"
            ) { backStackEntry ->
                AlbumDetailView(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentPadding = innerPadding,
                    viewModel = albumDetailViewModel,
                    albumID = backStackEntry.arguments?.getString("albumID") ?: ""
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArtistExplorerTopBar (
    currentView: ArtistExplorerAppView?,
    modifier: Modifier = Modifier,
    artistViewModel: ArtistViewModel = viewModel(),
    albumDetailViewModel: AlbumDetailViewModel = viewModel(),
) {
    val artist by artistViewModel.artist.collectAsState()
    val albumList by artistViewModel.albumList.collectAsState()
    val albumDetail by albumDetailViewModel.album.collectAsState()
    val trackList by albumDetailViewModel.trackList.collectAsState()

    CenterAlignedTopAppBar(
        title = {
            Text(
                text =
                    when (currentView) {
                        ArtistExplorerAppView.ArtistExplorerApp ->
                            if (artist == null || albumList.isEmpty()) {
                                "Loading..."
                            } else if (artist!!.isError) {
                                "Error"
                            } else {
                                artist!!.name
                            }
                        ArtistExplorerAppView.AlbumDetail ->
                            if (albumDetail == null || trackList.isEmpty()) {
                                "Loading..."
                            } else if (albumDetail!!.isError) {
                                "Error"
                            } else {
                                albumDetail!!.name
                            }
                        null -> "Artist Explorer"
                    },
                color = Color.White,
                textAlign = TextAlign.Center,
            )
        },
        modifier = modifier,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Black,
            titleContentColor = Color.White
        )
    )
}