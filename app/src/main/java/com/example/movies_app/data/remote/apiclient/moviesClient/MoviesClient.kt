package com.example.movies_app.data.remote.apiclient.moviesClient

import com.example.movies_app.data.remote.api.MoviesApiItem


interface MoviesClient {
    suspend fun getMoviesAll(): List<MoviesApiItem>
}