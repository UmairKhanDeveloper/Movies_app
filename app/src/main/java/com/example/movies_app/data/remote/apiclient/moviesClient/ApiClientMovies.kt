package com.example.movies_app.data.remote.apiclient.moviesClient

import com.example.movies_app.data.remote.api.MoviesApiItem
import com.example.movies_app.data.remote.apiclient.MovieDetails.moviesDetails
import com.example.movies_app.data.remote.apiclient.getGenres.genresResponse
import com.example.movies_app.data.remote.constant.Constant
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.statement.HttpResponse
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json

object ApiClientMovies {

    @OptIn(ExperimentalSerializationApi::class)
    private val client = HttpClient(Android) {
        install(ContentNegotiation) {
            json(
                Json {
                    isLenient = true
                    ignoreUnknownKeys = true
                    explicitNulls = false
                    prettyPrint = true
                }
            )
        }

        install(Logging) {
            level = LogLevel.ALL
            logger = object : Logger {
                override fun log(message: String) {
                    println(message)
                }
            }
        }

        install(HttpTimeout) {
            socketTimeoutMillis = Constant.TIMEOUT
            requestTimeoutMillis = Constant.TIMEOUT
            connectTimeoutMillis = Constant.TIMEOUT
        }
    }

    suspend fun getMovies(): List<MoviesApiItem> {
        val url = Constant.IMDB_BASE_URL

        val response: HttpResponse = client.get(url) {
            headers {
                append("x-rapidapi-host", Constant.RAPID_API_HOST)
                append("x-rapidapi-key", Constant.RAPID_API_KEY)
            }
        }

        return response.body()
    }

    suspend fun getGenres(): genresResponse {
        val url = "https://advanced-movie-search.p.rapidapi.com/genre/movie/list"

        return client.get(url) {
            headers {
                append("x-rapidapi-host", Constant.RAPID_API_HOST)
                append("x-rapidapi-key", Constant.RAPID_API_KEY)
            }
        }.body()
    }

    suspend fun getMovieDetails(movieId: Int): moviesDetails {
        val url = "https://advanced-movie-search.p.rapidapi.com/movies/getdetails?movie_id=$movieId"

        return client.get(url) {
            headers {
                append("x-rapidapi-host", Constant.RAPID_API_HOST)
                append("x-rapidapi-key", Constant.RAPID_API_KEY)
            }
        }.body()
    }



}