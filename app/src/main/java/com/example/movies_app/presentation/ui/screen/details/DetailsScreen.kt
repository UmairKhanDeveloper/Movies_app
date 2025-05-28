package com.example.movies_app.presentation.ui.screen.details

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.movies_app.R
import com.example.movies_app.presentation.ui.screen.home.ShareIcon

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Details(
    navController: NavHostController,
    image: String,
    title: String,
    rank: Int,
    year: Int,
    imdbid: String,
    description: String,
    genre: List<String>,
    id: String,
    rating: String,
    thumbnail: String,
    imdbLink: String
) {
    var showDialog by remember { mutableStateOf(false) }
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            confirmButton = {},
            dismissButton = {},
            containerColor = Color(0xFF1F1F2B),
            shape = RoundedCornerShape(16.dp),
            title = null,
            text = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.TopEnd
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close",
                            tint = Color.White,
                            modifier = Modifier
                                .size(20.dp)
                                .clickable { showDialog = false }
                        )
                    }

                    Text(
                        text = "Share to",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        ShareIcon(
                            icon = painterResource(id = R.drawable.facebook),
                            backgroundColor = Color(0xFF4267B2)
                        )
                        ShareIcon(
                            icon = painterResource(id = R.drawable.instagram),
                            backgroundColor = Brush.horizontalGradient(
                                listOf(Color(0xFFFEDA75), Color(0xFFD62976), Color(0xFF962FBF))
                            )
                        )
                        ShareIcon(
                            icon = painterResource(id = R.drawable.messenger),
                            backgroundColor = Color(0xFF00B2FF)
                        )
                        ShareIcon(
                            icon = painterResource(id = R.drawable.send),
                            backgroundColor = Color(0xFF1F1F2B),
                            iconTint = Color(0xFF00E0FF)
                        )
                    }
                }
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 80.dp)
            .verticalScroll(rememberScrollState())
            .background(Color(0xFF171725))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Box(
                    modifier = Modifier
                        .padding(end = 10.dp)
                        .clickable { navController.popBackStack() }
                        .padding(start = 10.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .size(32.dp)
                        .background(Color(0xFF252836)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.arrow_back),
                        contentDescription = "Back",
                        tint = Color.Red,
                        modifier = Modifier.size(24.dp)
                    )
                }
                Box(
                    modifier = Modifier
                        .padding(end = 10.dp)
                        .clickable { navController.popBackStack() }
                        .padding(start = 10.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .size(32.dp)
                        .background(Color(0xFF252836)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_heart),
                        contentDescription = "Back",
                        tint = Color.Red,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            AsyncImage(
                model = image,
                contentDescription = "Poster",
                modifier = Modifier
                    .height(287.dp)
                    .width(205.dp)
                    .clip(RoundedCornerShape(16.dp)),
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
            ) {
                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    InfoItem(icon = R.drawable.calendar, text = year.toString())
                    InfoItem(icon = R.drawable.time, text = "148 Minutes")
                    InfoItem(icon = R.drawable.film, text = genre.firstOrNull() ?: "Genre")
                }

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(start = 140.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.star),
                        contentDescription = "Rating",
                        tint = Color(0xFFFF8700),
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = rating, color = Color.White, fontSize = 14.sp)
                }

                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(
                        onClick = {
                            navController.navigate("VideoPlayerScreen/$imdbid")

                        },
                        colors = ButtonDefaults.buttonColors(Color(0xFFFF8700)),
                        modifier = Modifier
                            .weight(1f)
                            .height(56.dp)
                            .clip(RoundedCornerShape(14.dp))
                    ) {
                        Icon(Icons.Default.PlayArrow, contentDescription = "Play", tint = Color.White)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = "Play", color = Color.White)
                    }



                    Spacer(modifier = Modifier.width(12.dp))

                    IconButtonBox(icon = R.drawable.download) {   }
                    Spacer(modifier = Modifier.width(12.dp))
                    IconButtonBox(icon = R.drawable.share) {
                        showDialog = true
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 15.dp)
        ) {
            Text(
                text = "Story Line",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = description,
                color = Color(0xFFB0B0B0),
                fontSize = 14.sp,
                lineHeight = 20.sp
            )
            Text(
                text = "More",
                color = Color(0xFF12CDD9),
                fontSize = 14.sp,
                modifier = Modifier.padding(top = 4.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Cast and Crew",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
        }
    }
}



@Composable
fun InfoItem(icon: Int, text: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = null,
            tint = Color.Gray,
            modifier = Modifier.size(16.dp)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(text = text, color = Color.Gray, fontSize = 12.sp)
    }
}

@Composable
fun IconButtonBox(icon: Int,    onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(56.dp)
            .clip(RoundedCornerShape(14.dp))
            .background(Color(0xFF252836))
            .clickable {
                onClick()
            },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier.size(24.dp)
        )
    }
}


