package com.example.artistexplorerapp.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.artistexplorerapp.ui.model.TrackModel

@Composable
fun TrackCard(
    track: TrackModel,
    modifier: Modifier = Modifier,
    trackOrder: Int,
    trackDuration: String,
) {
    Card(
        modifier = modifier
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 0.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .padding(
                            vertical = 8.dp
                        )
                        .background(
                            shape = RoundedCornerShape(12.dp),
                            color = Color.Yellow.copy(
                                alpha = 0.1f
                            ),
                        )
                ) {
                    Text(
                        text = trackOrder.toString(),
                        color = Color.Yellow,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(
                                horizontal = 16.dp,
                                vertical = 4.dp
                            )
                    )
                }
                Spacer(
                    modifier = Modifier
                        .width(16.dp)
                )
                Text(
                    text = track.name ?: "Unknown Track",
                    fontSize = 16.sp,
                    color = Color.White
                )
            }
            Text(
                text = trackDuration,
                fontSize = 14.sp,
                color = Color.White
            )
        }
    }
}