package com.example.movies_app.data.reposotory

import com.example.movies_app.data.remote.api.MoviesApiItem
import com.example.movies_app.data.remote.apiclient.moviesClient.ApiClientMovies
import com.example.movies_app.data.remote.apiclient.moviesClient.MoviesClient

class Repository : MoviesClient {
    override suspend fun getMoviesAll(): List<MoviesApiItem> {
        return ApiClientMovies.getMovies()
    }
}
