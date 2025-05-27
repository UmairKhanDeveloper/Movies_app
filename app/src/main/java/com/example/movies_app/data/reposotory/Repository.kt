package com.example.movies_app.data.reposotory

import com.example.movies_app.data.remote.api.MoviesApiItem
import com.example.movies_app.data.remote.apiclient.MovieDetails.moviesDetails
import com.example.movies_app.data.remote.apiclient.getGenres.genresResponse
import com.example.movies_app.data.remote.apiclient.moviesClient.ApiClientMovies
import com.example.movies_app.data.remote.apiclient.moviesClient.MoviesClient

class Repository : MoviesClient {
    override suspend fun getMoviesAll(): List<MoviesApiItem> = ApiClientMovies.getMovies()
    override suspend fun getGenres(): genresResponse = ApiClientMovies.getGenres()
    override suspend fun getMovieDetails(movieId: Int): moviesDetails = ApiClientMovies.getMovieDetails(movieId)
}
