package com.example.movies_app.presentation.ui.screen.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.movies_app.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(navController: NavController) {
    var search by remember { mutableStateOf("") }
    val darkBackground = Color(0xFF1C1B2F)
    val tabItems = listOf("All", "Comedy", "Animation", "Dokumenter")
    val selectedTab = remember { mutableStateOf("All") }

    val movieList = listOf(
        Movie("Spider-Man No Way Home", "2021", "148 Minutes", "PG-13", true, "Action", R.drawable.bg1),
        Movie("The Jungle Waiting", "2021", "148 Minutes", "PG-13", false, "Action", R.drawable.bg1),
        Movie("Life of PI", "2021", "148 Minutes", "PG-13", false, "Action", R.drawable.bg1),
        Movie("Dot..", "2021", "148 Minutes", "PG-13", false, "Action", R.drawable.bg1)
    )

    val filteredMovies = remember(search) {
        if (search.isNotEmpty()) {
            movieList.filter {
                it.title.contains(search, ignoreCase = true) ||
                        it.category.contains(search, ignoreCase = true)
            }
        } else emptyList()
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(darkBackground)
            .padding(top = 30.dp, start = 16.dp, end = 16.dp, bottom = 80.dp)
    ) {
        item {
            TextField(
                value = search,
                onValueChange = { search = it },
                placeholder = { Text("Type title, categories, etc", color = Color.Gray) },
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color(0xFF2A293D),
                    cursorColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White
                ),
                leadingIcon = {
                    Image(
                        painter = painterResource(id = R.drawable.search),
                        contentDescription = "Search Icon",
                        modifier = Modifier.size(24.dp)
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(24.dp))
            )

            Spacer(modifier = Modifier.height(16.dp))
        }

        if (search.isNotEmpty()) {
            if (filteredMovies.isNotEmpty()) {
                items(filteredMovies) { movie ->
                    MovieSearchResultItem(movie)
                    Spacer(modifier = Modifier.height(16.dp))
                }
            } else {
                item {
                    Column(
                        modifier = Modifier
                            .background(Color(0XFF1F1D2B))
                            .fillMaxWidth()
                            .padding(10.dp),
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.no_results),
                            contentDescription = ""
                        )

                        Text(
                            text = "we are sorry, we can\n not find the movie :(",
                            fontSize = 16.sp,
                            color = Color.White,
                            fontWeight = FontWeight.SemiBold,
                            textAlign = TextAlign.Center
                        )

                        Text(
                            text = "Find your movie by Type title,\n categories, years, etc ",
                            fontSize = 12.sp,
                            color = Color(0XFF92929D),
                            fontWeight = FontWeight.Medium,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        } else {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState())
                ) {
                    tabItems.forEach { tab ->
                        Text(
                            text = tab,
                            color = if (tab == selectedTab.value) Color(0xFF00D9D5) else Color.White,
                            modifier = Modifier
                                .padding(end = 16.dp)
                                .clickable { selectedTab.value = tab },
                            fontWeight = if (tab == selectedTab.value) FontWeight.Bold else FontWeight.Normal
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                MovieSearchResultItem(movieList[0])

                Spacer(modifier = Modifier.height(54.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Recommend for you", color = Color.White, fontSize = 18.sp)
                    Text("See All", color = Color(0xFF00D9D5))
                }

                Spacer(modifier = Modifier.height(12.dp))
            }

            item {
                LazyRow {
                    items(movieList.drop(1)) { movie ->
                        MovieCard(title = movie.title, imageId = movie.imageRes)
                    }
                }
            }
        }
    }
}



@Composable
fun MovieCard(title: String, imageId: Int) {
    Column(
        modifier = Modifier
            .padding(end = 16.dp)
            .width(140.dp)
    ) {
        Box {
            Image(
                painter = painterResource(id = imageId),
                contentDescription = title,
                modifier = Modifier
                    .height(200.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )
            Box(
                modifier = Modifier
                    .padding(8.dp)
                    .background(Color(0xFF4B4B4B), RoundedCornerShape(4.dp))
                    .align(Alignment.TopStart)
            ) {
                Text(
                    "⭐ 4.5",
                    color = Color.White,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(4.dp)
                )
            }
        }
        Text(title, color = Color.White, maxLines = 1)
        Text("Action", color = Color.Gray, fontSize = 12.sp)
    }
}

@Composable
fun MovieSearchResultItem(movie: Movie) {
    Row {
        Image(
            painter = painterResource(id = movie.imageRes),
            contentDescription = movie.title,
            modifier = Modifier
                .size(width = 120.dp, height = 180.dp)
                .clip(RoundedCornerShape(12.dp)),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = if (movie.isPremium) "Premium" else "Free",
                color = Color.White,
                modifier = Modifier
                    .background(
                        if (movie.isPremium) Color(0xFFFF9800) else Color(0xFF00D9D5),
                        RoundedCornerShape(6.dp)
                    )
                    .padding(horizontal = 8.dp, vertical = 4.dp),
                fontSize = 12.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                movie.title,
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text("📅 ${movie.year}", color = Color.LightGray)
            Text("🕒 ${movie.duration}", color = Color.LightGray)
            Text(
                text = movie.rating,
                color = Color(0xFF00D9D5),
                modifier = Modifier
                    .border(1.dp, Color(0xFF00D9D5), RoundedCornerShape(4.dp))
                    .padding(horizontal = 6.dp, vertical = 2.dp),
                fontSize = 12.sp
            )
            Text("🎬 ${movie.category} | Movie", color = Color.LightGray)
        }
    }
}

data class Movie(
    val title: String,
    val year: String,
    val duration: String,
    val rating: String,
    val isPremium: Boolean,
    val category: String,
    val imageRes: Int
)
