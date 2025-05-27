package com.example.movies_app.presentation.ui.screen.search

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.movies_app.R
import com.example.movies_app.data.remote.api.MoviesApiItem
import com.example.movies_app.data.reposotory.Repository
import com.example.movies_app.domian.model.MainViewModel
import com.example.movies_app.firebase.ResultState

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun SearchScreen(navController: NavController) {
    var search by remember { mutableStateOf("") }
    val darkBackground = Color(0xFF1C1B2F)

    val repository2 = remember { Repository() }
    val viewModel2 = remember { MainViewModel(repository2) }
    val state2 by viewModel2.allMovies.collectAsState()
    var allWeatherData by remember { mutableStateOf<List<MoviesApiItem>>(emptyList()) }
    var isLoading by remember { mutableStateOf(false) }

    when (state2) {
        is ResultState.Error -> {
            isLoading = false
            val error = (state2 as ResultState.Error).error
            Text(text = "$error")
        }

        ResultState.Loading -> {
            isLoading = true
        }

        is ResultState.Success -> {
            isLoading = false
            val success = (state2 as ResultState.Success).response
            allWeatherData = success
        }
    }

    LaunchedEffect(Unit) {
        viewModel2.allMovies()
    }

    val filteredMovies = remember(search, allWeatherData) {
        if (search.isNotEmpty()) {
            allWeatherData.filter {
                it.title.contains(search, ignoreCase = true)
            }
        } else allWeatherData
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(darkBackground)
            .padding(top = 30.dp, start = 16.dp, end = 16.dp, bottom = 80.dp)
    ) {
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
                    modifier = Modifier.size(30.dp)
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(24.dp))
        )

        if (search.isEmpty()) {
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Recommend for you", color = Color.White, fontSize = 18.sp)
                Text("See All", color = Color(0xFF00D9D5))
            }
            Spacer(modifier = Modifier.height(12.dp))
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(vertical = 16.dp)
        ) {
            items(filteredMovies) { movie ->
                MovieCard(movie)
            }
        }
    }
}




@Composable
fun MovieCard(moviesApiItem: MoviesApiItem) {
    Column(
        modifier = Modifier
            .padding(end = 16.dp)
            .width(140.dp)
    ) {
        Box {
            AsyncImage(
                model = moviesApiItem.image,
                contentDescription = moviesApiItem.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(180.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp))
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
        Text(moviesApiItem.title, color = Color.White, maxLines = 1)
        Text("Action", color = Color.Gray, fontSize = 12.sp)
    }
}

