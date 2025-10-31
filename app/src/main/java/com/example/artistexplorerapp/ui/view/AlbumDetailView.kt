package com.example.artistexplorerapp.ui.view

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.artistexplorerapp.ui.route.ArtistExplorerAppView
import com.example.artistexplorerapp.ui.viewmodel.AlbumDetailViewModel

@Composable
fun AlbumDetailView(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    viewModel: AlbumDetailViewModel = viewModel(),
    albumID: String
) {
    LaunchedEffect(albumID) {
        viewModel.getAlbumDetail(albumID)
        viewModel.getTrackList(albumID)
    }

    val album by viewModel.album.collectAsState()
    val trackList by viewModel.trackList.collectAsState()

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                color = Color(0xFF282828)
            )
    ) {
        if (album == null || trackList.isEmpty()) {
            LoadingView()
        } else if (album!!.isError) {
            ErrorView(
                errorMessage = album!!.errorMessage
            )
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(contentPadding),
                contentPadding = PaddingValues(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    Column {
                        Spacer(
                            modifier = Modifier
                                .height(16.dp)
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(
                                    shape = RoundedCornerShape(12.dp)
                                )
                                .border(
                                    width = 2.dp,
                                    color = Color.Gray,
                                    shape = RoundedCornerShape(12.dp)
                                )
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                            ) {
                                AsyncImage(
                                    model = ImageRequest.Builder(LocalContext.current)
                                        .data(album!!.imagePath ?: "")
                                        .crossfade(true)
                                        .build(),
                                    contentDescription = album?.name,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .aspectRatio(1f)
                                )
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(
                                            all = 16.dp
                                        )
                                ) {
                                    Text(
                                        text = album!!.year,
                                        fontSize = 24.sp,
                                        maxLines = 1,
                                        color = Color.White
                                    )
                                    Spacer(
                                        modifier = Modifier.height(4.dp)
                                    )
                                    Text(
                                        text = "${album!!.year} • ${album!!.genre}",
                                        fontSize = 16.sp,
                                        maxLines = 1,
                                        color = Color.White
                                    )
                                    Spacer(
                                        modifier = Modifier.height(4.dp)
                                    )
                                    Text(
                                        text = album!!.description,
                                        fontSize = 12.sp,
                                        textAlign = TextAlign.Justify,
                                        color = Color.White,
                                        lineHeight = 16.sp
                                    )
                                }
                            }
                        }
                        Spacer(
                            modifier = Modifier
                                .height(16.dp)
                        )
                        Text(
                            text = "Tracks",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }
                items(trackList) { track ->
                    TrackCard(
                        modifier = Modifier
                            .fillMaxWidth(),
                        track = track,
                        trackOrder = trackList.indexOf(track) + 1,
                        trackDuration = viewModel.convertMillisToMinutesSeconds(track.duration)
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(2.dp)
                            .background(
                                color = Color.Gray
                            )
                    )
                    Spacer(
                        modifier = Modifier
                            .height(16.dp)
                    )
                }
            }
        }
    }
}