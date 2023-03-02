package com.example.domain

sealed class Resource<out T> {
    class Failure(val exception: Exception) : Resource<Nothing>()
    class Success<T>(val data: T) : Resource<T>()
}

inline fun <T> safeCall(block: () -> T): Resource<T> {
    return try {
        Resource.Success(data = block())
    } catch (e: Exception) {
        Resource.Failure(exception = e)
    }
}
