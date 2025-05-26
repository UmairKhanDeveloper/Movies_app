package com.example.movies_app.domian.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movies_app.data.remote.api.MoviesApiItem
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
}