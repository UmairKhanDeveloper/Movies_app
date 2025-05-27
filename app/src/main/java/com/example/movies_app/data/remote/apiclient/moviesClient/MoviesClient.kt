package com.example.movies_app.data.remote.apiclient.moviesClient

import com.example.movies_app.data.remote.api.MoviesApiItem
import com.example.movies_app.data.remote.apiclient.MovieDetails.moviesDetails
import com.example.movies_app.data.remote.apiclient.getGenres.genresResponse


interface MoviesClient {
    suspend fun getMoviesAll(): List<MoviesApiItem>
    suspend fun getGenres(): genresResponse
    suspend fun getMovieDetails(movieId: Int): moviesDetails
}
