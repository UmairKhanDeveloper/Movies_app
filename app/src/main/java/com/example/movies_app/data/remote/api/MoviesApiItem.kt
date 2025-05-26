package com.example.movies_app.data.remote.api


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MoviesApiItem(
    @SerialName("big_image")
    val bigImage: String,
    @SerialName("description")
    val description: String,
    @SerialName("genre")
    val genre: List<String>,
    @SerialName("id")
    val id: String,
    @SerialName("image")
    val image: String,
    @SerialName("imdb_link")
    val imdbLink: String,
    @SerialName("imdbid")
    val imdbid: String,
    @SerialName("rank")
    val rank: Int,
    @SerialName("rating")
    val rating: String,
    @SerialName("thumbnail")
    val thumbnail: String,
    @SerialName("title")
    val title: String,
    @SerialName("year")
    val year: Int
)