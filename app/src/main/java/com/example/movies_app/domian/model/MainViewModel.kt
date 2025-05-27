package com.example.movies_app.domian.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movies_app.data.remote.api.MoviesApiItem
import com.example.movies_app.data.remote.apiclient.MovieDetails.moviesDetails
import com.example.movies_app.data.remote.apiclient.getGenres.genresResponse
import com.example.movies_app.data.reposotory.Repository
import com.example.movies_app.firebase.ResultState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel(private val repository: Repository) : ViewModel() {

    private val _allMovies = MutableStateFlow<ResultState<List<MoviesApiItem>>>(ResultState.Loading)
    val allMovies: StateFlow<ResultState<List<MoviesApiItem>>> = _allMovies.asStateFlow()

    fun allMovies() {
        viewModelScope.launch {
            _allMovies.value = ResultState.Loading
            try {
                val response = repository.getMoviesAll()
                _allMovies.value = ResultState.Success(response)
            } catch (e: Exception) {
                _allMovies.value = ResultState.Error(e)
            }
        }
    }

    private val _genres = MutableStateFlow<ResultState<genresResponse>>(ResultState.Loading)
    val genres: StateFlow<ResultState<genresResponse>> = _genres.asStateFlow()

    private val _movieDetails = MutableStateFlow<ResultState<moviesDetails>>(ResultState.Loading)
    val movieDetails: StateFlow<ResultState<moviesDetails>> = _movieDetails.asStateFlow()

    fun getGenres() {
        viewModelScope.launch {
            _genres.value = ResultState.Loading
            try {
                val response = repository.getGenres()
                _genres.value = ResultState.Success(response)
            } catch (e: Exception) {
                _genres.value = ResultState.Error(e)
            }
        }
    }

    fun getMovieDetails(movieId: Int) {
        viewModelScope.launch {
            _movieDetails.value = ResultState.Loading
            try {
                val response = repository.getMovieDetails(movieId)
                _movieDetails.value = ResultState.Success(response)
            } catch (e: Exception) {
                _movieDetails.value = ResultState.Error(e)
            }
        }
    }



}