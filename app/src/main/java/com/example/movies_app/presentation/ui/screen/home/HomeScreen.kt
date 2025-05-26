package com.example.movies_app.presentation.ui.screen.home

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.movies_app.R
import com.example.movies_app.data.remote.api.MoviesApiItem
import com.example.movies_app.data.reposotory.Repository
import com.example.movies_app.domian.model.MainViewModel
import com.example.movies_app.firebase.ResultState
import com.example.movies_app.realtime_database.RealTimeDbRepository
import com.example.movies_app.realtime_database.RealTimeViewModel
import com.google.firebase.database.FirebaseDatabase

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(navController: NavController) {
    val context = LocalContext.current
    val databaseReference = FirebaseDatabase.getInstance().reference.child("your_node")
    val repository = remember { RealTimeDbRepository(databaseReference, context) }
    val viewModel = remember { RealTimeViewModel(repository) }
    val state = viewModel.res.value
    var textField by remember { mutableStateOf("") }
    val repository1 = remember { Repository() }
    val viewModel1 = remember { MainViewModel(repository1) }
    val state1 by viewModel1.allMovies.collectAsState()
    var allWeatherData by remember { mutableStateOf<List<MoviesApiItem>>(emptyList()) }
    var isLoading by remember { mutableStateOf(false) }

    when (state1) {
        is ResultState.Error -> {
            isLoading = false
            val error = (state1 as ResultState.Error).error
            Text(text = "$error")
        }

        ResultState.Loading -> {
            isLoading = true
        }

        is ResultState.Success -> {
            isLoading = false
            val success = (state1 as ResultState.Success).response
            allWeatherData = success
        }
    }


    Scaffold(topBar = {
        TopAppBar(title = {
            Column() {
                state.item.forEach {
                    Text(
                        text = "Hello, ${it.items.userName}",
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White,
                        fontSize = 16.sp
                    )
                }
                Text(
                    text = "Let’s stream your favorite movie",
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0XFF92929D),
                    fontSize = 12.sp
                )

            }

        }, actions = {
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
        }, navigationIcon = {
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
                    imageVector = Icons.Default.Home,
                    contentDescription = "Back",
                    tint = Color.Red,
                    modifier = Modifier.size(24.dp)
                )
            }
        }, colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0XFF1F1D2B)))
    }) {
        Column(
            modifier = Modifier
                .background(Color(0xFF1F1D2B))
                .padding(top = it.calculateTopPadding(), bottom = it.calculateTopPadding())
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(10.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color(0xFF262837)),
                value = textField,
                onValueChange = { textField = it },
                placeholder = {
                    Text("Searc a title..", color = Color.Gray)
                },
                colors = TextFieldDefaults.textFieldColors(
                    focusedTextColor = Color.White,
                    containerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                ),
                leadingIcon = {
                    Image(
                        painter = painterResource(id = R.drawable.search),
                        contentDescription = "Back",
                        modifier = Modifier.size(40.dp)
                    )
                },
                trailingIcon = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .padding(horizontal = 8.dp)
                                .width(1.dp)
                                .height(20.dp)
                                .background(Color.Gray.copy(alpha = 0.5f))
                        )
                        Image(
                            painter = painterResource(id = R.drawable.layer),
                            contentDescription = "Filter",
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            )

            Spacer(modifier = Modifier.height(10.dp))

            MovieCarousel()

            Spacer(modifier = Modifier.height(20.dp))

            CategoryScreen()

            MostPopularSection()

        }

    }
}

data class Movie(val imageUrl: String, val title: String)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MovieCarousel() {
    val movies = listOf(
        Movie(
            imageUrl = "https://i.pinimg.com/736x/e4/92/19/e49219defdb308bef411f29941275853.jpg",
            title = "Black Panther: Wakanda Forever"
        ),
        Movie(
            imageUrl = "https://mir-s3-cdn-cf.behance.net/projects/404/e39120182258869.Y3JvcCwyMTAwLDE2NDIsMCwxNTU.jpg",
            title = "Mission: Impossible – Dead Reckoning"
        ),
        Movie(
            imageUrl = "https://w0.peakpx.com/wallpaper/888/100/HD-wallpaper-the-foreigner-2017-jackie-chan-pierce-brosnan-poster-new-movies-action-movie-thumbnail.jpg",
            title = "The Foreigner"
        )
    )


    val pagerState = rememberPagerState(
        initialPage = 1,
        pageCount = { movies.size }
    )

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        HorizontalPager(
            state = pagerState,
            contentPadding = PaddingValues(horizontal = 32.dp),
            pageSpacing = 16.dp,
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
        ) { page ->
            val movie = movies[page]

            Box(
                modifier = Modifier.clip(RoundedCornerShape(10.dp))
            ) {
                AsyncImage(
                    model = movie.imageUrl,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .clip(RoundedCornerShape(topStart = 20.dp))
                        .fillMaxSize()
                )

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(Color.Transparent, Color.Black),
                                startY = 300f
                            )
                        )
                )

                Text(
                    text = movie.title,
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(12.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        DotsIndicator(totalDots = movies.size, selectedIndex = pagerState.currentPage)
    }
}


@Composable
fun DotsIndicator(totalDots: Int, selectedIndex: Int) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(totalDots) { index ->
            Box(
                modifier = Modifier
                    .padding(horizontal = 4.dp)
                    .size(
                        width = if (index == selectedIndex) 20.dp else 8.dp,
                        height = 8.dp
                    )
                    .clip(RoundedCornerShape(50))
                    .background(
                        if (index == selectedIndex) Color.Cyan else Color.Gray.copy(alpha = 0.5f)
                    )
            )
        }
    }
}

@Composable
fun CategorySelector(
    categories: List<String>,
    selectedCategory: String,
    onCategorySelected: (String) -> Unit
) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0XFF1F1D2B))
            .padding(horizontal = 8.dp, vertical = 8.dp)
    ) {
        items(categories) { category ->
            val isSelected = category == selectedCategory

            Box(
                modifier = Modifier
                    .padding(horizontal = 6.dp)
                    .clip(RoundedCornerShape(6.dp))
                    .background(
                        if (isSelected) Color(0xFF252836) else Color.Transparent
                    )
                    .clickable { onCategorySelected(category) }
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Text(
                    text = category,
                    color = if (isSelected) Color(0xFF12CDD9) else Color.White.copy(alpha = 0.7f),
                    fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal
                )
            }
        }
    }
}


@Composable
fun CategoryScreen() {
    var selected by remember { mutableStateOf("All") }
    val categories = listOf("All", "Comedy", "Animation", "Dokument", "Sci-Fi", "Drama")

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            "Categories",
            color = Color.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(10.dp)
        )

        CategorySelector(
            categories = categories,
            selectedCategory = selected,
            onCategorySelected = { selected = it }
        )
    }
}

@Composable
fun MostPopularSection() {
    val repository1 = remember { Repository() }
    val viewModel1 = remember { MainViewModel(repository1) }
    val state1 by viewModel1.allMovies.collectAsState()
    var allWeatherData by remember { mutableStateOf<List<MoviesApiItem>>(emptyList()) }
    var isLoading by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel1.allMovies()
    }

    when (state1) {
        is ResultState.Error -> {
            isLoading = false
            val error = (state1 as ResultState.Error).error
            Text(text = "$error")
        }

        ResultState.Loading -> {
            isLoading = true
        }

        is ResultState.Success -> {
            isLoading = false
            val success = (state1 as ResultState.Success).response
            allWeatherData = success
        }
    }

    Column(modifier = Modifier.padding(10.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                "Most popular",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Text("See All", color = Color.Cyan, fontWeight = FontWeight.SemiBold)
        }

        Spacer(modifier = Modifier.height(12.dp))


        LazyRow {
            items(allWeatherData) { movie ->
                MovieCard1(movie)
                Spacer(modifier = Modifier.width(12.dp))
            }
        }




    }
}

@Composable
fun MovieCard1(moviesApiItem: MoviesApiItem) {
    Box(
        modifier = Modifier
            .width(140.dp)
            .height(250.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFF252836))
    ) {
        Column {
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
                Row(
                    modifier = Modifier
                        .padding(6.dp)
                        .background(Color.Black.copy(alpha = 0.7f), RoundedCornerShape(10.dp))
                        .height(24.dp)
                        .padding(horizontal = 6.dp)
                        .align(Alignment.TopEnd),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.Star,
                        contentDescription = null,
                        tint = Color(0xFFFFA500),
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "${moviesApiItem.rating}",
                        color = Color.White,
                        fontSize = 12.sp
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text(
                    text = moviesApiItem.title,
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = moviesApiItem.genre.toString(),
                    color = Color.LightGray,
                    fontSize = 12.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}



