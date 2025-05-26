package com.example.movies_app.realtime_database

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movies_app.firebase.ResultState
import kotlinx.coroutines.launch

class RealTimeViewModel(private val repository: RealTimeRepository) : ViewModel() {

    private val _res: MutableState<itemsState> = mutableStateOf(itemsState())
    val res: State<itemsState> = _res

    fun insert(items: RealTimeUser.RealTimeItems) = repository.insert(items)

    init {
        viewModelScope.launch {
            repository.getItems().collect { result ->
                when (result) {
                    is ResultState.Error -> {
                        _res.value = itemsState(error = result.error.message ?: "Unknown error")
                    }
                    ResultState.Loading -> {
                        _res.value = itemsState(isLoading = true)
                    }
                    is ResultState.Success -> {
                        _res.value = itemsState(item = result.response)
                    }
                }
            }
        }
    }

    fun delete(key: String) = repository.delete(key)
    fun update(item: RealTimeUser) = repository.update(item)
}

data class itemsState(
    val item: List<RealTimeUser> = emptyList(),
    val error: String = "",
    val isLoading: Boolean = false
)