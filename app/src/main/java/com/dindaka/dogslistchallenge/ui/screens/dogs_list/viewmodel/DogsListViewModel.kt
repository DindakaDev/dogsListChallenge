package com.dindaka.dogslistchallenge.ui.screens.dogs_list.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dindaka.dogslistchallenge.domain.usecase.GetDogsUseCase
import com.dindaka.dogslistchallenge.ui.screens.dogs_list.state.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DogsListViewModel @Inject constructor(
    val dogsUseCase: GetDogsUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState

    init {
        loadData()
    }

    fun loadData(forceRefresh: Boolean = false) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            val result = dogsUseCase(forceRefresh)
            _uiState.value = when{
                 result.isSuccess -> {
                     val data = result.getOrNull()
                     when {
                         data == null -> UiState.Empty
                         else -> UiState.Success(data)
                     }
                 }
                result.isFailure -> {
                    UiState.Error(result.exceptionOrNull())
                }
                else -> {
                    UiState.Loading
                }
            }
        }
    }
}