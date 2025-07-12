package com.dindaka.dogslistchallenge.ui.screens.dogs_list.state

import com.dindaka.dogslistchallenge.data.model.DogData

sealed class UiState {
    object Loading : UiState()
    data class Success(val data: List<DogData>) : UiState()
    data class Error(val exception: Throwable? = null, val message: String? = null) : UiState()
    object Empty : UiState()
}