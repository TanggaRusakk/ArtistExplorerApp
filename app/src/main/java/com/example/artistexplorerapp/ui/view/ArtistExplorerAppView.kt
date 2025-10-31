package com.example.artistexplorerapp.ui.view

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.artistexplorerapp.ui.route.ArtistExplorerAppView
import com.example.artistexplorerapp.ui.viewmodel.AlbumDetailViewModel
import com.example.artistexplorerapp.ui.viewmodel.ArtistViewModel

@Composable
fun ArtistExplolerAppView(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    viewModel: ArtistViewModel = viewModel(),
    albumDetailViewModel: AlbumDetailViewModel = viewModel(),
    navController: NavController
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    LaunchedEffect(lifecycleOwner) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.RESUMED) {
            viewModel.loadArtist("John Mayer")
            viewModel.loadAlbumList("John Mayer")
        }
    }

    val artist by viewModel.artist.collectAsStateWithLifecycle()

    val albumList by viewModel.albumList.collectAsStateWithLifecycle()


    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                color = Color(0xFF282828)
            )
    ) {
        if (artist == null || albumList.isEmpty()) {
            LoadingView()
        } else if (artist!!.isError) {
            ErrorView(
                errorMessage = artist!!.errorMessage
            )
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(contentPadding),
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item(
                    span = {
                        GridItemSpan(maxLineSpan)
                    }
                ) {
                    Column {
                        Spacer(
                            modifier = Modifier
                                .height(16.dp)
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(1f)
                                .clip(
                                    shape = RoundedCornerShape(12.dp)
                                )
                        ) {
                            AsyncImage(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(artist?.imagePath ?: "")
                                    .crossfade(true)
                                    .build(),
                                contentDescription = artist?.name ?: "",
                                modifier = Modifier
                                    .fillMaxWidth(),
                                contentScale = ContentScale.Crop
                            )
                            Column(
                                modifier = Modifier
                                    .padding(
                                        start = 16.dp,
                                        bottom = 16.dp
                                    )
                                    .align(
                                        Alignment.BottomStart
                                    )
                            ) {
                                Text(
                                    text = artist!!.name,
                                    fontSize = 24.sp,
                                    color = Color.White
                                )
                                Spacer(
                                    modifier = Modifier
                                        .height(4.dp)
                                )
                                Text(
                                    text = artist!!.genre,
                                    fontSize = 16.sp,
                                    color = Color.White
                                )
                            }
                        }
                        Spacer(
                            modifier = Modifier
                                .height(16.dp)
                        )
                        Text(
                            text = artist!!.biografi,
                            fontSize = 12.sp,
                            textAlign = TextAlign.Justify,
                            color = Color.White,
                            lineHeight = 16.sp
                        )
                        Spacer(
                            modifier = Modifier
                                .height(16.dp)
                        )
                        Text(
                            text = "Albums",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }
                items(albumList) { album ->
                    AlbumCard(
                        modifier = Modifier
                            .fillMaxWidth(),
                        album = album,
                        onCardClick = {
                            albumDetailViewModel.resetAlbumDetail()
                            navController.navigate(
                                "${ArtistExplorerAppView.AlbumDetail}/${album.id}"
                            )
                        }
                    )
                }
            }
        }
    }
}

