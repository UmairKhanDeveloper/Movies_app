package com.example.movies_app.data.remote.apiclient.getGenres


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class genresResponse(
    @SerialName("genres")
    val genres: List<Genre>
)