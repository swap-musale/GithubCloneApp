package com.example.ghclone

sealed class UIState<out T> {
    object Empty : UIState<Nothing>()
    object Loading : UIState<Nothing>()
    class Failure(val exception: Exception) : UIState<Nothing>()
    class Success<T>(val data: T) : UIState<T>()
}
