package com.example.githubcloneapp.util

sealed class UiState<out T> {
    object Loading : UiState<Nothing>()
    object EmptyList : UiState<Nothing>()
    data class Success<out T>(val data: T) : UiState<T>()
    class Error(val exception: Exception) : UiState<Nothing>()
}